package ru.nachos.core.controller.lib;

import org.opengis.referencing.operation.MathTransform;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.lib.Weather;
import ru.nachos.db.services.GeometryDatabaseService;

public interface InitialPreprocessingData {
    GeometryDatabaseService getGeometryService();
    Config getConfig();
    Fire getFire();
    Weather getWeather();
    Network getNetwork();
    FireSpreadCalculator getCalculator();
    InitialPreprocessingData preprocessing();
    MathTransform getTransformation();
    MathTransform getReTransformation();
    void setSRID(int srid);
    int getDatabaseSRID();
}
