package ru.nachos.core.fire.algorithms;

import ru.nachos.core.config.lib.Config.FireSpreadModels;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.lib.Weather;

public class FireSpreadCalculatorFactory {

    public static FireSpreadCalculator getCalculator(FireSpreadModels model, Network network, Weather weather){
        FireSpreadCalculator calculator;
        switch (model){
            case Rotermel:
            default:
                calculator = new Rotermel(network, weather);
        }
        return calculator;
    }

}
