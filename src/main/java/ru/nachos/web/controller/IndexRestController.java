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

    @GetMapping("/weather/city/{id}")
    public ResponseEntity<City> getCityId(@PathVariable("id") String id, Model model){
        RestTemplate template = new RestTemplate();
        City s = null;
        try {
            ResponseEntity<OpenWeatherMap> weather = template.getForEntity(
                    new URI("https://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=6206177780dd6e1df5b26d31e5ab0553"),
                    OpenWeatherMap.class);
            s = weather.getBody().getCity();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
