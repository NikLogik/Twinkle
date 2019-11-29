package ru.nachos.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nachos.web.models.IndexHtmlImpl;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.lib.EstimateDataService;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/twinkle")
public class IndexRestController {

    @Autowired
    EstimateDataService dataService;
    private final AtomicLong id = new AtomicLong();

    @GetMapping(value = "/fires")
    public IndexHtmlImpl indexHtml(){
        return new IndexHtmlImpl(id.incrementAndGet(), "Some text for greeting");
    }

    @PostMapping(value = "/fires", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultData> postFireData(@RequestBody EstimateData estimateData){
        boolean valid = dataService.validationData(estimateData);
        ResultData resultData = null;
        if (valid){
            dataService.start(estimateData);
            resultData = dataService.getResult();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
