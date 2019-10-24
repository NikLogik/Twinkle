package ru.nachos.core.weather.lib;

import ru.nachos.core.Id;

public interface WeatherFactory {

    WeatherData createWeatherData(Id<WeatherData> id);


}
