package ru.nachos.models;

import java.io.Serializable;
import java.util.Map;


/**
 * Class for mapping response about weather from
 * OpenWeatherMap.org
 */
public class WeatherJSON implements Serializable {


    Map<String, Object> city;

    Map<String, Object> list;


}
