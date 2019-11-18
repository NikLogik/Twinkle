package ru.nachos.core.controller;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;

final class ControllerImpl implements Controller {

    private static Controller controller;

    private Config config;
    private InitialPreprocessingData preprocessingData;
    public static final String DIVIDER = "###################################################";
    final String MARKER = "#####";
    private int currentIteration;

    ControllerImpl(){}

    ControllerImpl(InitialPreprocessingData preprocessing){
        this.config = preprocessing.getConfig();
        this.preprocessingData = preprocessing;
    }

    @Override
    public InitialPreprocessingData getPreprocessingData() { return this.preprocessingData; }
    @Override
    public Config getConfig() { return this.config; }
    @Override
    public void run(){
        throw new UnsupportedOperationException();
    }
}
