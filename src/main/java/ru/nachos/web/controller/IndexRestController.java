package ru.nachos.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.IndexHtmlImpl;
import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.EstimateDataServiceImpl;
import ru.nachos.web.services.lib.EstimateDataService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
public class IndexRestController {

    EstimateDataService dataService;
    private final AtomicLong id = new AtomicLong();

    @GetMapping(value = "/fires")
    public IndexHtmlImpl indexHtml(){
        return new IndexHtmlImpl(id.incrementAndGet(), "Some text for greeting");
    }

    @PostMapping(value = "/fires", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CoordinateJson>> postFireData(@RequestBody EstimateData estimateData){
        dataService = new EstimateDataServiceImpl();
        boolean valid = dataService.validationData(estimateData);
        ResultData resultData;
        if (valid){
            dataService.start(estimateData);
            resultData = dataService.getResult();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CoordinateJson> collect = resultData.getFirstEntry().stream().map(AgentIterData::getCordinates).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @GetMapping(value = "fires/{iterNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CoordinateJson>> getFireDataByIterNum(@PathVariable(name = "iterNum") int iter){
        ResultData resultData = dataService.getResult();
        List<CoordinateJson> collect = resultData.getAgents().get(iter).stream().map(AgentIterData::getCordinates).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }
}
