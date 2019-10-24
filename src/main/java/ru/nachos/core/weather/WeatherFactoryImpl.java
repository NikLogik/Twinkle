package ru.nachos.core.weather;

import ru.nachos.core.Id;
import ru.nachos.core.weather.lib.WeatherData;
import ru.nachos.core.weather.lib.WeatherFactory;

public class WeatherFactoryImpl implements WeatherFactory {

    @Override
    public WeatherData createWeatherData(Id<WeatherData> id) {return new WeatherDataImpl(id);}
}
