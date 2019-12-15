package ru.nachos.web.services;

import ru.nachos.core.FireModelRunner;
import ru.nachos.core.FireModelRunnerImpl;
import ru.nachos.web.models.lib.RequestData;
import ru.nachos.web.services.lib.RequestDataService;
import ru.nachos.web.services.lib.ResponseDataService;

public class RequestDataServiceImpl implements RequestDataService {

    private FireModelRunner runner;
    private ResponseDataService responseService;

    public RequestDataServiceImpl() {
        responseService = new ResponseDataServiceImpl();
        runner = new FireModelRunnerImpl(responseService);
    }

    @Override
    public boolean validateData(RequestData requestData) {
        if (requestData.getFireCenter() == null || requestData.getFireCenter().size() < 1){
            return false;
        }
        if (requestData.getFireClass() == 0 || requestData.getFireClass() > 5){
            return false;
        }
        if (requestData.getIterationStepTime() == 0){
            return false;
        }
        if (requestData.getLastIterationTime() == 0){
            return false;
        }
        if (requestData.getWindSpeed() == 0){
            return false;
        }
        return true;
    }
    @Override
    public ResponseDataService getResponseService() { return responseService; }
    @Override
    public void start(RequestData requestData) {
        runner.run(requestData);
    }
}
