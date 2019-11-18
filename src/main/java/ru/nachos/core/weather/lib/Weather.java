package ru.nachos.core.weather.lib;

import ru.nachos.core.controller.lib.HasID;

public interface Weather extends HasID {

    double getWindDirection();
    double getWindSpeed();
    double getTemperature();
    double getHumidity();
}
