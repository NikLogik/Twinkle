package ru.nachos.core.weather.lib;

import ru.nachos.core.Id;

import java.util.Date;
import java.util.Map;

public interface Weather {

    Map<Id<WeatherData>, WeatherData> getWeatherDates();
    WeatherData getWeatherByStartTime(Date startTime);
    WeatherFactory getFactory();
}
