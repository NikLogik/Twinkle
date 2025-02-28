package git.niklogik.core.weather;

import git.niklogik.core.Id;
import git.niklogik.core.config.lib.Config;

import static java.lang.String.format;

public class WeatherUtils {

    private WeatherUtils() {}

    public static Weather createWeather(Config config) {
        return new Weather(
            Id.create(format("%s:weather", config.getFireName()), Weather.class),
            config.getWindDirection(),
            config.getWindSpeed().doubleValue(),
            config.getTemperature(),
            config.getHumidity()
        );
    }
}
