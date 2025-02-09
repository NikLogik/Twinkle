package git.niklogik.core.controller;

import git.niklogik.core.Id;
import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.algorithms.FireSpreadCalculator;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.fire.lib.FireFactory;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.core.utils.GeodeticCalculator;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import git.niklogik.db.services.ContourLineService;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.db.services.GeometryDatabaseService;
import org.geotools.geometry.jts.JTS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class InitialPreprocessingDataLoader {

    private final Logger logger = LoggerFactory.getLogger(InitialPreprocessingDataLoader.class);

    private final Config config;
    private final InitialPreprocessingData preprocessingData;
    private final GeometryDatabaseService geometryService;
    private final FireDatabaseService fireService;
    private final ContourLineService lineService;

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
        ForestFuelTypeDao forestFuelTypeDao = fireService.getForestFuelType(preprocessingData.getConfig().getFuelType());
        preprocessingData.getNetwork().setFuelType(forestFuelTypeDao);
        double speed = calculator.calculateSpeedOfSpreadWithConstraint(forestFuelTypeDao, config.getWindSpeed());
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
