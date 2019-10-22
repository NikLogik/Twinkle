package ru.nachos.core.controller;

import ru.nachos.core.fire.Fire;
import ru.nachos.core.network.Network;
import ru.nachos.core.weather.Weather;

public interface Controller {

    Fire getFire();

    Weather getWeather();

    Network getNetwork();

}
