package ru.nachos.core.controller;

import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.db.services.FireDatabaseService;

public final class ControllerUtils {

    public static Controller createController(InitialPreprocessingData data, FireDatabaseService fireService){
        return new ControllerImpl(data, fireService);
    }

}
