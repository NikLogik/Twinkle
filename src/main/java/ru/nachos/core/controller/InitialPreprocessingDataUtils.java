package ru.nachos.core.controller;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.db.services.GeometryDatabaseService;

public class InitialPreprocessingDataUtils {

    public static InitialPreprocessingData createInitialData(Config config, GeometryDatabaseService geometryService){
        if (config == null){
            throw new NullPointerException("Config must not be null");
        } else {
            return new InitialPreprocessingDataImpl(config, geometryService);
        }
    }

    public static InitialPreprocessingData loadInitialData(InitialPreprocessingData data){
        InitialPreprocessingDataLoader loader = new InitialPreprocessingDataLoader(data);
        return loader.loadPreprocessingData();
    }

    public static void resetToNull(InitialPreprocessingDataImpl data){
        data.setFire(null);
        data.setCalculator(null);
        data.setNetwork(null);
        data.setWeather(null);
        data.setConfig(null);
        data.setReTransformation(null);
        data.setTransformation(null);
    }
}
