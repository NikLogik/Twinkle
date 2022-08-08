package ru.nachos.core.controller;

import org.apache.log4j.Logger;
import org.geotools.geometry.jts.JTS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.operation.TransformException;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.fire.lib.FireFactory;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.db.entities.fire.ForestFuelType;
import ru.nachos.db.services.ContourLineService;
import ru.nachos.db.services.FireDatabaseService;
import ru.nachos.db.services.GeometryDatabaseService;

import java.util.Map;

class InitialPreprocessingDataLoader {

    private Logger logger = Logger.getLogger(InitialPreprocessingDataLoader.class);

    private Config config;
    private InitialPreprocessingData preprocessingData;
    private GeometryDatabaseService geometryService;
    private FireDatabaseService fireService;
    private ContourLineService lineService;

    InitialPreprocessingDataLoader(InitialPreprocessingData preprocessingData, GeometryDatabaseService geometryService,
                                   FireDatabaseService fireService, ContourLineService lineService) {
        this.config = preprocessingData.getConfig();
        this.preprocessingData = preprocessingData;
        this.geometryService = geometryService;
        this.fireService = fireService;
        this.lineService = lineService;
    }

    InitialPreprocessingData loadPreprocessingData(){
        this.loadFire();
        this.loadNetwork();
        return this.preprocessingData;
    }

    private void loadNetwork() {
        double maxDistance = preprocessingData.getFire().getFireSpeed() * (config.getEndTime()/30.);
        Coordinate[] boundaryBox = NetworkUtils.calculateBoundaryBox(preprocessingData.getFire().getCenterPoint(), maxDistance);
        logger.info("<========================= Load network from boundary box " + boundaryBox[0] + " " + boundaryBox[1] + " " +
                boundaryBox[2] + " " + boundaryBox[3] + " " + "=========================>");
        geometryService.createNetworkFromBoundaryBox(preprocessingData.getNetwork(), boundaryBox);
        lineService.getContourLines(preprocessingData.getNetwork(), boundaryBox);
        NetworkUtils.createTrips(preprocessingData.getNetwork(), preprocessingData.getFire().getTwinkles(), preprocessingData.getConfig().getEndTime(), preprocessingData.getCalculator());
    }

    private void loadFire() {
        Fire fire = preprocessingData.getFire();
        try {
            fire.setCenterPoint(JTS.transform(fire.getCenterPoint(), null, preprocessingData.getTransformation()));
        } catch (TransformException e) {
            e.printStackTrace();
        }
        FireFactory factory = fire.getFactory();
        FireSpreadCalculator calculator = preprocessingData.getCalculator();
        double headFireDirection = GeodeticCalculator.convertDirection(preprocessingData.getWeather().getWindDirection());
        FireUtils.setHeadDirectionOfSpread(fire, headFireDirection);
        ForestFuelType forestFuelType = fireService.getForestFuelType(preprocessingData.getConfig().getFuelType());
        preprocessingData.getNetwork().setFuelType(forestFuelType);
        double clearSpeed = calculator.calculateSpeedWithoutExternalConstraint(forestFuelType);
        double speed = calculator.calculateSpeedOfSpreadWithConstraint(clearSpeed, config.getWindSpeed());
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
