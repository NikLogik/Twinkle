package git.niklogik.core.controller.lib;

import git.niklogik.core.config.lib.Config;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.weather.Weather;
import org.opengis.referencing.operation.MathTransform;
import git.niklogik.core.fire.algorithms.FireSpreadCalculator;
import git.niklogik.core.network.lib.Network;

public interface InitialPreprocessingData {
    Config getConfig();
    Fire getFire();
    Weather getWeather();
    Network getNetwork();
    FireSpreadCalculator getCalculator();
    MathTransform getTransformation();
    MathTransform getReTransformation();
    void setSRID(int srid);
    int getOsmDatabaseSRID();
}
