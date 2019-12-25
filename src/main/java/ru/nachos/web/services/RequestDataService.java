package ru.nachos.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.nachos.core.FireModelRunner;
import ru.nachos.web.models.lib.RequestData;

@SessionScope
@Service
public class RequestDataService {

    private final FireModelRunner runner;

    @Autowired
    public RequestDataService(FireModelRunner runner) {
        this.runner = runner;
    }

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
}
