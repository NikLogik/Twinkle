package ru.nachos.core.weather;


/**
 * Container for storing data about weather in the fire area
 */
public class WeatherData {
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
     * THe current humidity in percents
     */
    private double humidity;

    private WeatherData(){}

    public static class WeatherDataBuilder{


        private double windDirection;
        private double windSpeed;
        private double temperature;
        private double humidity;

        public WeatherDataBuilder(double windDirection, double windSpeed, double temperature) {
            this.windDirection = windDirection;
            this.windSpeed = windSpeed;
            this.temperature = temperature;
        }

        public WeatherDataBuilder setHumidity(double humidity){
            this.humidity = humidity;
            return this;
        }

        public WeatherData build(){
            return new WeatherData(this);
        }
    }
     private WeatherData(WeatherDataBuilder builder){
        this.windDirection = builder.windDirection;
        this.windSpeed = builder.windSpeed;
        this.temperature = builder.temperature;
        this.humidity = builder.humidity;
     }

    public double getWindDirection() {
        return windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
