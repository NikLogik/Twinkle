package ru.nachos.core.controller;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.algorithms.FireSpreadCalculatorFactory;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.WeatherUtils;
import ru.nachos.core.weather.lib.Weather;

public class InitialPreprocessingDataImpl implements InitialPreprocessingData {

    private Config config;
    private Fire fire;
    private Weather weather;
    private Network network;
    private FireSpreadCalculator calculator;

    InitialPreprocessingDataImpl(Config config) {
        this.config = config;
        this.fire = FireUtils.createFire(this.config);
        this.network = NetworkUtils.createNetwork();
        this.weather = WeatherUtils.createWeather(this.config);
        this.calculator = FireSpreadCalculatorFactory.getCalculator(config.getCalculator(), this.network, this.weather);
    }

    @Override
    public Config getConfig() { return config; }
    @Override
    public Fire getFire() { return fire; }

    void setFire(Fire fire) { this.fire = fire; }
    @Override
    public Weather getWeather() { return weather; }
    @Override
    public Network getNetwork() { return network; }
    @Override
    public FireSpreadCalculator getCalculator() {return calculator; }
    @Override
    public InitialPreprocessingData preprocessing(){
        return InitialPreprocessingDataUtils.loadInitialData(this);
    }
}
