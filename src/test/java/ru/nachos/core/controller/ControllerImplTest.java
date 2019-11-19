package ru.nachos.core.controller;

import org.junit.Before;
import org.junit.Test;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.lib.Fire;

import static org.junit.Assert.*;

public class ControllerImplTest {

    private InitialPreprocessingData data;

    @Before
    public void init(){
        InitialPreprocessingData data = InitialPreprocessingDataUtils.createInitialData(ConfigUtils.createConfig());
        data.preprocessing();
    }

    @Test
    public void doIterationTest(){
        Controller controller = ControllerUtils.createController(data);
        controller.run();
        Fire fire = controller.getFire();
        InitialPreprocessingData preprocessingData = controller.getPreprocessingData();
    }


}