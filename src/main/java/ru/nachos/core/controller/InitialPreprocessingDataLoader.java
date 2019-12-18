package ru.nachos.core.controller;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;
import ru.nachos.core.DatabaseManager;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.fire.lib.FireFactory;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.ForestFuelType;
import ru.nachos.core.utils.GeodeticCalculator;

import java.util.Map;

@Service
class InitialPreprocessingDataLoader {

    private Config config;
    private InitialPreprocessingData preprocessingData;

    InitialPreprocessingDataLoader(InitialPreprocessingData preprocessingData) {
        this.config = preprocessingData.getConfig();
        this.preprocessingData = preprocessingData;
    }

    InitialPreprocessingData loadPreprocessingData(){
        this.loadFire();
        this.loadNetwork();
        return this.preprocessingData;
    }

    private void loadNetwork() {
        double maxDistance = preprocessingData.getFire().getFireSpeed() * config.getEndTime();
        Coordinate[] boundaryBox = NetworkUtils.calculateBoundaryBox(preprocessingData.getFire().getCenterPoint(), maxDistance);
        NetworkUtils.createNetwork(preprocessingData.getNetwork(), boundaryBox);
    }

    private void loadFire() {
        Fire fire = preprocessingData.getFire();
        try {
            fire.setCenterPoint(JTS.transform(fire.getCenterPoint(), null, preprocessingData.getTransformation()));
        } catch (TransformException e) {
            e.printStackTrace();
        }
        FireFactory factory = fire.getFactory();
        //Получаем данные по типу сгораемого материала из базы данных
        int typeCode = preprocessingData.getConfig().getFuelType().getValue();
        ForestFuelType fuelType = DatabaseManager.findForestFuelTypeByCode(typeCode);
        //считаем общую скорость для пожара, без учета внешних факторов
        FireSpreadCalculator calculator = preprocessingData.getCalculator();
        double headFireDirection = GeodeticCalculator.convertDirection(preprocessingData.getWeather().getWindDirection());
        FireUtils.setHeadDirectionOfSpread(fire, headFireDirection);
        double clearSpeed = calculator.calculateSpeedWithoutExternalConstraint(fuelType);
        double speed = calculator.calculateSpeedOfSpreadWithConstraint(clearSpeed, config.getWindSpeed(), false);
        fire.setFireSpeed(speed);
        //генерируем агентов по длине периметра и ставим на стартовые точки
        Map<Id<Agent>, Agent> idAgentMap = factory.generateFireFront(fire.getCenterPoint(), fire.getAgentDistance(),
                                            fire.getPerimeter(), headFireDirection);
        fire.getTwinkles().putAll(idAgentMap);
        //Считаем скорость агентов относительно направления ветра
        for (Agent agent : fire.getTwinkles().values()){
            calculator.calculateSpeedOfSpreadWithArbitraryDirection(speed, agent, headFireDirection);
        }
    }
}
