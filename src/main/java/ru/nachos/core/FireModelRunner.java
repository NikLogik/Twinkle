package ru.nachos.core;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPoint;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.ControllerUtils;
import ru.nachos.core.controller.InitialPreprocessingDataUtils;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.db.model.fire.FireModel;
import ru.nachos.db.repository.fire.ContourLineRepository;
import ru.nachos.db.services.ContourLineService;
import ru.nachos.db.services.FireDatabaseService;
import ru.nachos.db.services.GeometryDatabaseService;
import ru.nachos.web.models.lib.RequestData;

import java.util.List;

@RequestScope
@Service
public class FireModelRunner {

    Logger logger = Logger.getLogger(FireModelRunner.class);

    private GeometryDatabaseService geometryService;
    private FireDatabaseService fireService;
    private ContourLineService lineService;
    private ContourLineRepository lineRepository;
    private FireModel model;
    @Value("${app.database.osm.srid}")
    private int osmDatabaseSrid;

    @Autowired
    public FireModelRunner(GeometryDatabaseService geometryService, FireDatabaseService fireService,
                           ContourLineService lineService, ContourLineRepository lineRepository){
        this.geometryService = geometryService;
        this.fireService = fireService;
        this.lineService = lineService;
        this.lineRepository = lineRepository;
    }

    public void run(RequestData requestData) {
        Coordinate fireCenterCoordinate = new Coordinate(calculateFireCenter(requestData.getFireCenter()));
        int perimeter = fireService.firePerimeter(requestData.getFireCenter(), fireCenterCoordinate);
        int startTime = 0;
        int lastIteration = requestData.getLastIterationTime() / requestData.getIterationStepTime();
        Config config = new ConfigUtils.ConfigBuilder(ConfigUtils.createConfig())
                .setStartTime(startTime)
                .setEndTime(requestData.getLastIterationTime())
                .setSRID(osmDatabaseSrid)
                .setIterationStepTime(requestData.getIterationStepTime())
                .setFireAgentDIstance(requestData.getFireAgentDistance())
                .setFuelType(requestData.getFuelTypeCode())
                .setFireClass(requestData.getFireClass())
                .setWindDirection(requestData.getWindDirection())
                .setWindSpeed(requestData.getWindSpeed())
                .setFireCenterCoordinate(fireCenterCoordinate)
                .setLastIteration(lastIteration)
                .setFirePerimeter(perimeter)
                .build();
        InitialPreprocessingData initialData = InitialPreprocessingDataUtils.createInitialData(config, osmDatabaseSrid);
        InitialPreprocessingDataUtils.loadInitialData(initialData, geometryService, fireService, lineService);
        Controller controller = ControllerUtils.createController(initialData, fireService);
        controller.run();
        this.model = controller.getModel();
    }

    private Coordinate calculateFireCenter(List<Coordinate> coordinateList){
        if (coordinateList.size() == 1){
            return coordinateList.get(0);
        } else if (coordinateList.size() == 2){
            return NetworkUtils.centerLine(coordinateList.get(0), coordinateList.get(1));
        } else {
            GeometryFactory factory = new GeometryFactory();
            MultiPoint multiPoint = factory.createMultiPoint(coordinateList.toArray(new Coordinate[0]));
            return multiPoint.getCentroid().getCoordinate();
        }
    }

    public FireModel getModel() {
        return model;
    }
}
