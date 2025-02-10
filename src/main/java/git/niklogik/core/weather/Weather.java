package git.niklogik.core.weather;

import git.niklogik.core.Id;
import git.niklogik.core.config.lib.Config;

public record Weather(
    Id<Weather> id,
    /**
     * The direction of wind in degrees
     */
    Double windDirection,
    /**
     * The speed of wind in meters/sec
     */
    Double windSpeed,
    /**
     * The current temperature in Celsius('C')
     */
    Double tempCelsius,
    /**
     * The current humidity in percents
     */
    Double humidity) {}
