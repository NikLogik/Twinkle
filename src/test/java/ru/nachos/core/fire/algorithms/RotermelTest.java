package ru.nachos.core.fire.algorithms;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nachos.core.DatabaseManager;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.InitialPreprocessingDataUtils;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.network.lib.ForestFuelType;

public class RotermelTest {

    @Autowired
    private InitialPreprocessingDataUtils utils;
    private InitialPreprocessingData data;

    @Before
    public void init(){
        data = utils.createInitialData(ConfigUtils.createConfig());
    }

    @Test
    public void calculateConcreteSpeedOfSpread() {

    }

    @Test
    public void calculateSpeedWithoutExternalConstraint() {
        Config.ForestFuelType fuelType = data.getConfig().getFuelType();
        ForestFuelType typeByCode = DatabaseManager.findForestFuelTypeByCode(802);
        Rotermel rotermel = new Rotermel(data.getNetwork());
        double speed = rotermel.calculateSpeedWithoutExternalConstraint(typeByCode);
        System.out.println(speed);
        Assert.assertNotNull(speed);
    }
}
