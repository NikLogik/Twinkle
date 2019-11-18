package ru.nachos.core.controller.lib;

import ru.nachos.core.config.lib.Config;

public interface Controller extends Runnable{

    InitialPreprocessingData getPreprocessingData();

    Config getConfig();
}
