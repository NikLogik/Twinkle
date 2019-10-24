package ru.nachos.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.nachos.web.models.Wind;
import ru.nachos.owm.OpenWeatherMap;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(ModelMap model) throws URISyntaxException {
        //обязательно инициализировать класс, иначе будет вылетать IllegalArgumentException при загрузке страницы
        model.addAttribute("wind", new Wind());
        return "index";
    }

    /**
     * Для маппинга несольких форм на разные POST методы, нужно хранить состояние класса, который является моделью представления,
     * в отдельной переменной класса контроллера, чтобы между разными POST запросами не терялись данные, уже полученные ранее.
     * Также маппинг происходит за счет добавления в каждую форму скрытого <input> с параметрами name=value,
     * и добавления в аннотации PostMapping параметра params="key=value", по соответствию с которым будет выбираться подходящий
     * POST метод
     */
    @PostMapping(value = "/index")
    public String addWeatherInfo(@RequestParam(name = "windSpeed") String windSpeed,
                                 @RequestParam(name = "direction") String direction,
                                 Model model){
        Wind wind = new Wind(windSpeed, direction);
        model.addAttribute(wind);
        return "index";
    }


    /* как альтернатива сразу делать байндинг на модель

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addWeatherInfo(@ModelAttribute Wind wind, Model model){
        model.addAttribute(wind);
        return "index";
    }
    */

    @GetMapping(value = "/index", params = "load-weather")
    public String loadWeather(Model model){
        /**
         * Загрузка данных из OpenWeatherMap.org.
         * RestTemplate принимает, через заданный URI, JSON объект и парсит его в шаблонный класс.
         * Класс должен иметь структуру, аналогичную структуре JSON`а, либо не указанные в шаблоне поля будут
         * проигнорированы при парсинге
         */
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<OpenWeatherMap> weather = template.getForEntity(
                        new URI("https://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=6206177780dd6e1df5b26d31e5ab0553"),
                        OpenWeatherMap.class);
            model.addAttribute("city", weather.getBody().getCity().getName());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        model.addAttribute("wind", new Wind());
        return "index";
    }
}
