package ru.nachos.web.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.nachos.db.services.FireDatabaseService;
import ru.nachos.web.models.lib.ResponseData;

@SessionScope
@Service
public class ResponseDataService {

    Logger logger = Logger.getLogger(ResponseDataService.class);

    private final FireDatabaseService fireService;
    @Autowired
    public ResponseDataService(FireDatabaseService fireService) {
        this.fireService = fireService;
    }

    public ResponseData getResponseDataByFireIdAndIterNumber(long fireId, int iterNumber){
        return fireService.getResponseDataByFireIdAndIterNumber(fireId, iterNumber);
    }

    public void deleteFireModelByFireId(long fireId){
        fireService.deleteFireModelByFireId(fireId);
    }
}
