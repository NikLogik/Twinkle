package ru.nachos.core.controller;

import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.lib.Weather;

public final class ControllerImpl implements Controller {

    private static Controller controller;

    private Fire fire;

    private Weather weather;

    private Network network;

    private ControllerImpl(){}

    @Override
    public Fire getFire() { return this.fire; }

    @Override
    public Weather getWeather() {
        return this.weather;
    }

    @Override
    public Network getNetwork() {
        return this.network;
    }

    public static Controller getInstance(){
        if (controller == null)
            controller = new ControllerImpl();
        return controller;
    }
}