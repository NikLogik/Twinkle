package ru.nachos.core.controller.lib;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.lib.Weather;

public interface InitialPreprocessingData {
    Config getConfig();
    Fire getFire();
    Weather getWeather();
    Network getNetwork();
    FireSpreadCalculator getCalculator();
    InitialPreprocessingData preprocessing();
}