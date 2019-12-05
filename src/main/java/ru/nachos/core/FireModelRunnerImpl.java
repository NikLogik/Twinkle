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
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.info.IterationInfo;
import ru.nachos.core.info.IterationInfoPrinter;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.ResultDataServiceImpl;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FireModelRunnerImpl implements FireModelRunner{

    private IterationInfo printer = new IterationInfoPrinter();
    private ResultDataServiceImpl resultService;
    private ResultData resultData;
    private Map<Id<Agent>, Agent> agents = new LinkedHashMap<>();

    public FireModelRunnerImpl() {
        this.resultService = new ResultDataServiceImpl();
    }

    public void run(EstimateData estimateData) {
        resultData = null;
        Coordinate fireCenterCoordinate = new Coordinate(calculateFireCenter(estimateData.getFireCenter()));
        Calendar calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int startTime = second + (minute * 60) + (hour * 3600);
        int lastIteration = (estimateData.getLastIterationTime() - startTime) / estimateData.getIterationStepTime() ;
        Config config = new ConfigUtils.ConfigBuilder(ConfigUtils.createConfig())
                .setStartTime(startTime)
                .setEndTime(estimateData.getLastIterationTime())
                .setIterationStepTime(estimateData.getIterationStepTime())
                .setFireAgentDIstance(estimateData.getFireAgentDistance())
                .setFuelType(estimateData.getFuelTypeCode())
                .setFireClass(estimateData.getFireClass())
                .setWindDirection(estimateData.getWindDirection())
                .setWindSpeed(estimateData.getWindSpeed())
                .setFireCenterCoordinate(fireCenterCoordinate)
                .setLastIteration(lastIteration)
                .build();
        InitialPreprocessingData initialData = InitialPreprocessingDataUtils.createInitialData(config);
        InitialPreprocessingDataUtils.loadInitialData(initialData);
        printer.info(config, initialData);
        Controller controller = ControllerUtils.createController(initialData);
        controller.run();
        System.out.println(config.toString());
        this.agents.putAll(controller.getFire().getTwinkles());
        resultData = resultService.prepareResult(agents, initialData);
        ConfigUtils.resetToNull(config);
        InitialPreprocessingDataUtils.resetToNull((InitialPreprocessingDataImpl) initialData);
    }

    @Override
    public ResultData getResultData() {
        return this.resultData;
    }

    private Coordinate calculateFireCenter(List<Coordinate> coordinateList){
        if (coordinateList.size() == 1){
            return coordinateList.get(0);
        } else if (coordinateList.size() == 2){
            return NetworkUtils.centerLine(coordinateList.get(0), coordinateList.get(1));
        } else {
            GeometryFactory factory = new GeometryFactory();
            MultiPoint multiPoint = factory.createMultiPoint(coordinateList.toArray(new Coordinate[0]));
            double radius = Math.sqrt(multiPoint.getArea() / Math.PI);
            return multiPoint.getCentroid().getCoordinate();
        }
    }
}
