package ru.nachos.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nachos.core.FireModelRunner;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseDataImpl;
import ru.nachos.web.models.lib.RequestData;
import ru.nachos.web.models.lib.ResponseData;
import ru.nachos.web.services.RequestDataService;
import ru.nachos.web.services.ResponseDataService;

@RestController
public class IndexRestController {

    Logger logger = Logger.getLogger(IndexRestController.class);

    private ResponseDataService responseService;
    private RequestDataService requestService;
    private FireModelRunner fireRunner;

    @Autowired
    public IndexRestController(ResponseDataService responseService, RequestDataService requestService, FireModelRunner runner) {
        this.responseService = responseService;
        this.requestService = requestService;
        this.fireRunner = runner;
    }

    @GetMapping(value = "/fires")
    public ResponseEntity<ResponseData> testRequest(){
        return new ResponseEntity<>(new ResponseDataImpl(1, 1, 1, new CoordinateJson[0]), HttpStatus.OK);
    }

    @PostMapping(value = "/fires", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> postFireData(@RequestBody RequestData requestData){
        if (requestService.validateData(requestData)){
            logger.info("Get estimate data :" + requestData.toString());
            fireRunner.run(requestData);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        ResponseData response = responseService.getFireModelFirstIteration(fireRunner.getModelId());
        logger.info("Send response with result of the first iteration");
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @GetMapping(value = "/fires/{fireId}/{iterNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getFireDataByIterNum(@PathVariable(name = "fireId") long fireId,
                                                             @PathVariable(name = "iterNum") int iter){
        ResponseData response = responseService.getResponseDataByFireIdAndIterNumber(fireId, iter);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/fires/{fireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFireModel(@PathVariable(name = "fireId") long fireId){
        responseService.deleteFireModelByFireId(fireId);
    }
}