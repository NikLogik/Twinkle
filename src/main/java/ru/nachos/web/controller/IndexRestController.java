package ru.nachos.web.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseData;
import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.EstimateDataServiceImpl;
import ru.nachos.web.services.lib.EstimateDataService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class IndexRestController {

    Logger logger = Logger.getLogger(IndexRestController.class);

    private ResultData resultData;
    private EstimateDataService dataService;

    @PostMapping(value = "/fires", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> postFireData(@RequestBody EstimateData estimateData){
        if (resultData != null){
            resultData = null;
        }
        dataService = new EstimateDataServiceImpl();
        if (dataService.validationData(estimateData)){
            logger.info("Get estimate data :" + estimateData.toString());
            dataService.start(estimateData);
            resultData = dataService.getResult();
            if (resultData == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int number = resultData.getFirstIterationNumber();
        List<CoordinateJson> collect = resultData.getFirstIteration().stream().map(AgentIterData::getCordinates).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).collect(Collectors.toList());
        logger.info("Send response with result of the first iteration");
        return new ResponseEntity<>(new ResponseData(resultData.getIterCount(), number, collect), HttpStatus.OK);
    }

    @GetMapping(value = "/fires/{iterNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getFireDataByIterNum(@PathVariable(name = "iterNum") int iter){
        if (resultData != null) {
            List<CoordinateJson> collect = resultData.getIterationByNumber(iter).stream().map(AgentIterData::getCordinates).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).collect(Collectors.toList());
            return new ResponseEntity<>(new ResponseData(resultData.getIterCount(), iter, collect), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
