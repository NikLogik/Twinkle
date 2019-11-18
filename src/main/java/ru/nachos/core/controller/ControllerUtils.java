package ru.nachos.core.controller;

import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;

public final class ControllerUtils {

    public static Controller createController(){ return new ControllerImpl(); }

    public static Controller createController(InitialPreprocessingData data){ return new ControllerImpl(data); }

}
