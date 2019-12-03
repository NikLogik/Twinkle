package ru.nachos.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;

import javax.annotation.PostConstruct;

@Component
public class InitialPreprocessingDataUtils {

    private static InitialPreprocessingData data;
    @Autowired
    private InitialPreprocessingData source_data;

    public static InitialPreprocessingData createInitialData(Config config){
        if (config == null){
            throw new NullPointerException("Config must not be null");
        } else {
            return data.create(config);
        }
    }

    public static InitialPreprocessingData loadInitialData(InitialPreprocessingData data){
        InitialPreprocessingDataLoader loader = new InitialPreprocessingDataLoader(data);
        return loader.loadPreprocessingData();
    }

    @PostConstruct
    public void init() {
        data = source_data;
    }

    public static void resetToNull(InitialPreprocessingDataImpl data){
        data.setFire(null);
        data.setCalculator(null);
        data.setNetwork(null);
        data.setWeather(null);
        data.setConfig(null);
        data.setRepository(null);
        data.setReTransformation(null);
        data.setTransformation(null);
    }
}
