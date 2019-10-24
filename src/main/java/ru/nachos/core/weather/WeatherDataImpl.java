package ru.nachos.core.weather;


import ru.nachos.core.Id;
import ru.nachos.core.weather.lib.WeatherData;

import java.util.Date;

/**
 * Container for storing data about weather in the fire area
 */
class WeatherDataImpl implements WeatherData {

    private Id<WeatherData> id;
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

    private Date timeFrom;
    private Date timeTo;

    WeatherDataImpl(Id<WeatherData> id){
        this.id = id;
    }

    void setWindDirection(double direction){this.windDirection = direction;}
    void setWindSpeed(double speed){this.windSpeed = speed;}
    void setTemperature(double temperature){this.temperature = temperature;}
    void setHumidity(double humidity ) {this.humidity = humidity;}
    void setTimeFrom(Date timeFrom){this.timeFrom = timeFrom;}
    void setTimeTo(Date timeTo){this.timeTo = timeTo;}

    @Override
    public Id<WeatherData> getId() { return id; }
    @Override
    public double getWindDirection() { return windDirection; }
    @Override
    public double getWindSpeed() {return windSpeed;}
    @Override
    public double getTemperature() {return temperature;}
    @Override
    public double getHumidity() {return humidity;}
    @Override
    public Date getTimeFrom() { return timeFrom; }
    @Override
    public Date getTimeTo() { return timeTo; }
}
