package ru.nachos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nachos.models.Wind;

@Controller
public class MainController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model){
        //обязательно инициализировать класс, иначе будет вылетать IllegalArgumentException при загрузке страницы
        model.addAttribute("wind", new Wind());
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addWeatherInfo(@ModelAttribute Wind wind, Model model){

        model.addAttribute(wind);
        return "index";
    }
}
