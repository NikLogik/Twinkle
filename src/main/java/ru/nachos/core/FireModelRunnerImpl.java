package ru.nachos.core;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPoint;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.ControllerUtils;
import ru.nachos.core.controller.InitialPreprocessingDataImpl;
import ru.nachos.core.controller.InitialPreprocessingDataUtils;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.web.models.lib.RequestData;
import ru.nachos.web.services.lib.ResponseDataService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FireModelRunnerImpl implements FireModelRunner{

//    Logger logger = Logger.getLogger(FireModelRunnerImpl.class);

    private ResponseDataService responseService;
    private Map<Integer, AgentMap> agents = new TreeMap<>();

    public FireModelRunnerImpl(ResponseDataService responseService) {
        this.responseService = responseService;
    }

    public void run(RequestData requestData) {
        Coordinate fireCenterCoordinate = new Coordinate(calculateFireCenter(requestData.getFireCenter()));
        int startTime = 0;
        int lastIteration = requestData.getLastIterationTime() / requestData.getIterationStepTime();
        Config config = new ConfigUtils.ConfigBuilder(ConfigUtils.createConfig())
                .setStartTime(startTime)
                .setEndTime(requestData.getLastIterationTime())
                .setIterationStepTime(requestData.getIterationStepTime())
                .setFireAgentDIstance(requestData.getFireAgentDistance())
                .setFuelType(requestData.getFuelTypeCode())
                .setFireClass(requestData.getFireClass())
                .setWindDirection(requestData.getWindDirection())
                .setWindSpeed(requestData.getWindSpeed())
                .setFireCenterCoordinate(fireCenterCoordinate)
                .setLastIteration(lastIteration)
                .build();
        InitialPreprocessingData initialData = InitialPreprocessingDataUtils.createInitialData(config);
        InitialPreprocessingDataUtils.loadInitialData(initialData);
        Controller controller = ControllerUtils.createController(initialData);
        controller.run();
        this.agents.putAll(controller.getIterationMap());
        responseService.prepareResult(this.agents, initialData.getReTransformation());
//        logger.warn("Delete IterationInfoPrinter before deploy to server");
        ConfigUtils.resetToNull(config);
        InitialPreprocessingDataUtils.resetToNull((InitialPreprocessingDataImpl) initialData);
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
}
