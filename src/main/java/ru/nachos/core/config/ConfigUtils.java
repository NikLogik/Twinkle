package ru.nachos.core.config;

import ru.nachos.core.config.lib.Config;

public class ConfigUtils {

    public static Config createConfig(){
        return new ConfigImpl();
    }

}
