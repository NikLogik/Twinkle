package ru.nachos.core.controller;

import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.lib.Weather;

public interface Controller {

    Fire getFire();

    Weather getWeather();

    Network getNetwork();

}
