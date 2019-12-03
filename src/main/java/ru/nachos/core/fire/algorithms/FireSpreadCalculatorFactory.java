package ru.nachos.core.fire.algorithms;

import ru.nachos.core.config.lib.Config.FireSpreadModels;
import ru.nachos.core.network.lib.Network;

public class FireSpreadCalculatorFactory {

    public static FireSpreadCalculator getCalculator(FireSpreadModels model, Network network){
        FireSpreadCalculator calculator;
        switch (model){
            case Rotermel:
            default:
                calculator = new Rotermel(network);
        }
        return calculator;
    }

}
