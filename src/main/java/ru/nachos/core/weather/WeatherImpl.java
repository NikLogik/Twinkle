package ru.nachos.core.weather;

import ru.nachos.core.Id;
import ru.nachos.core.weather.lib.Weather;
import ru.nachos.core.weather.lib.WeatherData;
import ru.nachos.core.weather.lib.WeatherFactory;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

class WeatherImpl implements Weather {

    private Map<Id<WeatherData>, WeatherData> weatherDates;
    private WeatherFactory factory;

    WeatherImpl(WeatherFactory factory){this.factory = factory;}

    @Override
    public WeatherData getWeatherByStartTime(Date startTime) {
        Optional<WeatherData> weatherData = weatherDates.values().stream()
                                        .filter(date -> date.getTimeFrom().getTime() == startTime.getTime())
                                        .findFirst();
        return weatherData.orElse(null);
    }

    @Override
    public Map<Id<WeatherData>, WeatherData> getWeatherDates() {return this.weatherDates;}

    @Override
    public WeatherFactory getFactory() {return factory;}
}
