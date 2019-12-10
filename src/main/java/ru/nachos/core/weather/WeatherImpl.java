package ru.nachos.core.weather;

import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.weather.lib.Weather;

class WeatherImpl implements Weather {

    private Id<Weather> id;
    /**
     * The direction of wind in degrees
     */
    private double windDirection;
    /**
     * The speed of wind in meters/sec
     */
    private double windSpeed;
    /**
     * The current temperature in Celsium('C')
     */
    private double temperature;
    /**
     * The current humidity in percents
     */
    private double humidity;
    WeatherImpl(Config config){
        this.id = Id.create(config.getFireName() + ":weather", Weather.class);
        this.windDirection = config.getWindDirection();
        this.windSpeed = config.getWindSpeed();
        this.temperature = config.getTemperature();
        this.humidity = config.getHumidity();
    }

    @Override
    public Id<Weather> getId() {return this.id;}

    @Override
    public double getWindDirection() { return this.windDirection; }
    @Override
    public double getWindSpeed() { return this.windSpeed; }
    @Override
    public double getTemperature() { return this.temperature; }
    @Override
    public double getHumidity() { return this.humidity; }

}
