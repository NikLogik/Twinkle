package ru.nachos.experimental;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.nachos.owm.City;
import ru.nachos.owm.OpenWeatherMap;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class IndexRestController {

    @GetMapping("/weather/city/{id}")
    public City getCityId(@PathVariable("id") String id, Model model){
        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<OpenWeatherMap> weather = template.getForEntity(
                    new URI("https://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=6206177780dd6e1df5b26d31e5ab0553"),
                    OpenWeatherMap.class);
            City s = weather.getBody().getCity();
            return s;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
