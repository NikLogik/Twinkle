package ru.nachos.core.fire.algorithms;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.controller.InitialPreprocessingDataUtils;
import ru.nachos.core.controller.lib.InitialPreprocessingData;

public class RotermelTest {

    @Autowired
    private InitialPreprocessingDataUtils utils;
    private InitialPreprocessingData data;

    public void init(){
        data = utils.createInitialData(ConfigUtils.createConfig());
    }

//    @Test
//    public void calculateConcreteSpeedOfSpread() {
//
//    }
//
//    @Test
//    public void calculateSpeedWithoutExternalConstraint() {
//        Config.ForestFuelType fuelType = data.getConfig().getFuelType();
//        ForestFuelType typeByCode = DatabaseManager.findForestFuelTypeByCode(802);
//        Rotermel rotermel = new Rotermel(data.getNetwork());
//        double speed = rotermel.calculateSpeedWithoutExternalConstraint(typeByCode);
//        System.out.println(speed);
//        Assert.assertNotNull(speed);
//    }
}
