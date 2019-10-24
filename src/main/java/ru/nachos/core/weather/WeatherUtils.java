package ru.nachos.core.weather;

import ru.nachos.core.controller.ControllerImpl;
import ru.nachos.core.weather.lib.Weather;
import ru.nachos.core.weather.lib.WeatherData;
import ru.nachos.core.weather.lib.WeatherFactory;

import java.util.Date;

public class WeatherUtils {

    private WeatherUtils(){}

    public static final WeatherFactory factory = ControllerImpl.getInstance().getWeather().getFactory();

    public static WeatherFactory getFactory(){return factory;}

    public static Weather createWeather(){
        return new WeatherImpl(new WeatherFactoryImpl());
    }

    public static final class WeatherDataBuilder{

        private WeatherDataImpl data;

        public WeatherDataBuilder(WeatherData data, double windDirection, double windSpeed, double humidity) {
            this.data = (WeatherDataImpl) data;
            this.data.setWindDirection(windDirection);
            this.data.setWindSpeed(windSpeed);
            this.data.setHumidity(humidity);
        }

        public WeatherDataBuilder setTimeFrom(Date timeFrom){
            this.data.setTimeFrom(timeFrom);
            return this;
        }

        public WeatherDataBuilder setTimeTo(Date timeTo){
            this.data.setTimeTo(timeTo);
            return this;
        }

        public WeatherDataBuilder setHumidity(double temperature){
            this.data.setTemperature(temperature);
            return this;
        }

        public WeatherData build(){
            return this.data;
        }
    }
}
