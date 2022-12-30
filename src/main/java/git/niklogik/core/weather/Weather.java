package git.niklogik.core.weather;

import git.niklogik.core.Id;
import git.niklogik.core.config.lib.Config;

public class Weather {

    private Id<Weather> id;
    /**
     * The direction of wind in degrees
     */
    private final double windDirection;
    /**
     * The speed of wind in meters/sec
     */
    private final double windSpeed;
    /**
     * The current temperature in Celsium('C')
     */
    private final double temperature;
    /**
     * The current humidity in percents
     */
    private final double humidity;
    Weather(Config config){
        this.id = Id.create(config.getFireName() + ":weather", Weather.class);
        this.windDirection = config.getWindDirection();
        this.windSpeed = config.getWindSpeed();
        this.temperature = config.getTemperature();
        this.humidity = config.getHumidity();
    }

    public Id<Weather> getId() {return this.id;}
    public double getWindDirection() { return this.windDirection; }
    public double getWindSpeed() { return this.windSpeed; }
    public double getTemperature() { return this.temperature; }
    public double getHumidity() { return this.humidity; }

}
