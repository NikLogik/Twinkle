package git.niklogik.core.controller;

import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.algorithms.FireSpreadCalculator;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.core.utils.GeodeticCalculator;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import git.niklogik.db.services.ContourLineService;
import git.niklogik.db.services.CoordinateTransformationService;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.db.services.GeometryDatabaseService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static git.niklogik.db.services.CoordinateTransformationService.TransformDirection.FIRE_DB_TO_OSM_DB;

@Service
@RequiredArgsConstructor
public class InitialPreprocessingDataLoader {

    private final Logger logger = LoggerFactory.getLogger(InitialPreprocessingDataLoader.class);

    private final GeometryDatabaseService geometryService;
    private final FireDatabaseService fireService;
    private final ContourLineService lineService;
    private final CoordinateTransformationService transformationService;

    public void loadPreprocessingData(Config config, InitialPreprocessingData preprocessingData) {
        this.loadFire(config, preprocessingData);
        this.loadNetwork(config, preprocessingData);
    }

    private void loadNetwork(Config config, InitialPreprocessingData preprocessingData) {
        double maxDistance = preprocessingData.getFire().getFireSpeed().doubleValue() * (config.getEndTime() / 30.);
        Coordinate[] boundaryBox = NetworkUtils.calculateBoundaryBox(preprocessingData.getFire().getCenterPoint(),
                                                                     maxDistance);
        logger.info("<========================= Load network from boundary box {} {} {} {} =========================>",
                    boundaryBox[0], boundaryBox[1], boundaryBox[2], boundaryBox[3]);
        geometryService.createNetworkFromBoundaryBox(preprocessingData.getNetwork(), boundaryBox);
        lineService.getContourLines(preprocessingData.getNetwork(), boundaryBox);
        NetworkUtils.createTrips(preprocessingData.getNetwork(), preprocessingData.getFire().getTwinkles(),
                                 preprocessingData.getConfig().getEndTime(), preprocessingData.getCalculator());
    }

    private void loadFire(Config config, InitialPreprocessingData preprocessingData) {
        Fire fire = preprocessingData.getFire();

        var mathTransformation = transformationService.getMathTransformation(FIRE_DB_TO_OSM_DB);
        var transformedCenter = transformationService.transformAsNew(fire.getCenterPoint(), mathTransformation);
        fire.setCenterPoint(transformedCenter);

        var factory = fire.getFactory();
        double headFireDirection = GeodeticCalculator.convertDirection(preprocessingData.getWeather().windDirection());
        FireUtils.setHeadDirectionOfSpread(fire, headFireDirection);
        ForestFuelTypeDao forestFuelTypeDao = fireService.getForestFuelType(
            preprocessingData.getConfig().getFuelType());
        preprocessingData.getNetwork().setFuelType(forestFuelTypeDao);

        FireSpreadCalculator calculator = preprocessingData.getCalculator();
        var speed = calculator.freeFireSpeed(forestFuelTypeDao, config.getWindSpeed());
        fire.setFireSpeed(speed);

        //генерируем агентов по длине периметра и ставим на стартовые точки
        Map<UUID, Agent> idAgentMap = factory.generateFireFront(fire.getCenterPoint(), fire.getAgentDistance(),
                                                                fire.getPerimeter(), headFireDirection);
        fire.getTwinkles().putAll(idAgentMap);
        //Считаем скорость агентов относительно направления ветра
        for (Agent agent : fire.getTwinkles().values()) {
            var agentSpeed = calculator.calculateForDirection(speed, agent.getDirection());
            agent.setSpeed(agentSpeed);
        }
    }
}
