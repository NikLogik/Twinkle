package git.niklogik.core.weather;

import git.niklogik.core.config.lib.Config;

public class WeatherUtils {

    private WeatherUtils(){}

    public static Weather createWeather(Config config){
        return new Weather(config);
    }
}
