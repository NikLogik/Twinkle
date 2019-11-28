package ru.nachos.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.nachos.experimental.AgentIterDataWriter;
import ru.nachos.owm.City;
import ru.nachos.owm.OpenWeatherMap;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.lib.EstimateDataService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class IndexRestController {
    @Autowired
    AgentIterDataWriter writer;
    @Autowired
    EstimateDataService dataService;

    @PostMapping("/fires")
    public ResponseEntity<ResultData> postFireData(@RequestBody EstimateData estimateData){
        boolean valid = dataService.validationData(estimateData);
        ResultData resultData = null;
        if (valid){
            dataService.start(estimateData);
            resultData = dataService.getResult();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        writer.writeData(resultData);
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
