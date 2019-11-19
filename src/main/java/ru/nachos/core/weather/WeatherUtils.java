package ru.nachos.core.weather;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.weather.lib.Weather;

public class WeatherUtils {

    private WeatherUtils(){}

    public static Weather createWeather(Config config){
        return new WeatherImpl(config);
    }

}
