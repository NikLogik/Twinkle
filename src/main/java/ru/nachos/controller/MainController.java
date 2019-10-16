package ru.nachos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nachos.models.Wind;

@Controller
public class MainController {

    @GetMapping(value = "/index")
    public String index(Model model){
        //обязательно инициализировать класс, иначе будет вылетать IllegalArgumentException при загрузке страницы
        model.addAttribute("wind", new Wind());
        return "index";
    }

    //параметр params нужен, если несколько POST-методов используют одинаковое value, но request идет от разных форм
    @PostMapping(value = "/index", params = "formId=weather-form")
    public String addWeatherInfo(@RequestParam(name = "windSpeed") String windSpeed,
                                 @RequestParam(name = "direction") String direction,
                                 @RequestParam(name = "formId") String formId,
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
}
