package ru.nachos.core.weather.lib;

import ru.nachos.core.Id;

import java.util.Date;

public interface WeatherData {

    Id<WeatherData> getId();
    double getWindDirection();
    double getWindSpeed();
    double getTemperature();
    double getHumidity();
    Date getTimeFrom();
    Date getTimeTo();
}
