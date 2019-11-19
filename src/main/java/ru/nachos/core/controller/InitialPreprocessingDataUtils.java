package ru.nachos.core.controller;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;

public class InitialPreprocessingDataUtils {

    public static InitialPreprocessingData createInitialData(Config config){
        if (config == null){
            throw new NullPointerException("Config must not be null");
        } else {
            return new InitialPreprocessingDataImpl(config);
        }
    }

    public static InitialPreprocessingData loadInitialData(InitialPreprocessingData data){
        InitialPreprocessingDataLoader loader = new InitialPreprocessingDataLoader(data);
        return loader.loadPreprocessingData();
    }
}
