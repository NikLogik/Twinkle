package ru.nachos.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.FireModelRunner;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.lib.EstimateDataService;

@Service
public class EstimateDataServiceImpl implements EstimateDataService {

    @Autowired
    private FireModelRunner runner;

    @Override
    public boolean validationData(EstimateData estimateData) {
        if (estimateData.getCoordinates() == null || estimateData.getCoordinates().size() < 1){
            return false;
        }
        if (estimateData.getFireClass() == 0 || estimateData.getFireClass() > 5){
            return false;
        }
        if (estimateData.getIterationStepTime() == 0){
            return false;
        }
        if (estimateData.getLastIterationTime() == 0){
            return false;
        }
        if (estimateData.getWindDirection() == 0){
            return false;
        }
        if (estimateData.getWindSpeed() == 0){
            return false;
        }
        return true;
    }

    @Override
    public void start(EstimateData estimateData) {
        runner.run(estimateData);
    }

    @Override
    public ResultData getResult() {
        return runner.getResultData();
    }
}
