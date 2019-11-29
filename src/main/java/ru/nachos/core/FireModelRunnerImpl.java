package ru.nachos.core;

import com.vividsolutions.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.ControllerUtils;
import ru.nachos.core.controller.InitialPreprocessingDataUtils;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.lib.ResultDataService;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class FireModelRunnerImpl implements FireModelRunner{

    @Autowired
    private ResultDataService resultService;
    private ResultData resultData;
    private Map<Id<Agent>, Agent> agents = new LinkedHashMap<>();

    public void run(EstimateData estimateData) {
        Coordinate fireCenterCoordinate = new Coordinate(44.97385, 33.88063);
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
        Controller controller = ControllerUtils.createController(initialData);
        controller.run();
        this.agents.putAll(controller.getFire().getTwinkles());
        resultData = resultService.prepareResult(agents, config);
    }

    @Override
    public ResultData getResultData() {
        return this.resultData;
    }
}
