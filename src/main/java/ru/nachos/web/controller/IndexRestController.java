package ru.nachos.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseDataImpl;
import ru.nachos.web.models.lib.RequestData;
import ru.nachos.web.models.lib.ResponseData;
import ru.nachos.web.services.RequestDataServiceImpl;
import ru.nachos.web.services.lib.RequestDataService;
import ru.nachos.web.services.lib.ResponseDataService;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class IndexRestController {

//    Logger logger = Logger.getLogger(IndexRestController.class);

    private ResponseDataService responseService;
    private RequestDataService requestService;

    @GetMapping(value = "/fires")
    public ResponseEntity<ResponseData> testRequest(){
        ArrayList<CoordinateJson> list = new ArrayList<>();
        list.add(new CoordinateJson(2.00, 3.00));
        return new ResponseEntity<>(new ResponseDataImpl(1, 1, new CoordinateJson[0]), HttpStatus.OK);
    }

    @PostMapping(value = "/fires", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> postFireData(@RequestBody RequestData requestData){
        requestService = new RequestDataServiceImpl();
        if (requestService.validateData(requestData)){
//            logger.info("Get estimate data :" + requestData.toString());
            requestService.start(requestData);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        responseService = requestService.getResponseService();
        Map.Entry<Integer, ResponseData> responseByFirstIteration = responseService.getResponseByFirstIteration();
        if (responseByFirstIteration == null || responseByFirstIteration.getValue()==null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        logger.info("Send response with result of the first iteration");
        return new ResponseEntity<>(responseByFirstIteration.getValue(), HttpStatus.OK);
    }

    @GetMapping(value = "/fires/{iterNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getFireDataByIterNum(@PathVariable(name = "iterNum") int iter){
        if (responseService != null && responseService.containsIter(iter)) {
            ResponseData responseDataByIter = responseService.getResponseDataByIter(iter);
            return new ResponseEntity<>(responseDataByIter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
