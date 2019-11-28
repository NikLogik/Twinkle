package ru.nachos.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nachos.core.network.lib.ForestFuelType;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManagerTest {

    public static Map<Integer, ForestFuelType> fuelTypeMap = new HashMap<>();
    @Autowired
    private static DatabaseManager manager;

    @Before
    public void init(){
        fuelTypeMap.putAll(DatabaseManager.getFuelTypeMap());
    }

    @Test
    public void findFuelTypeByIdTest(){
        int code = 303;
        ForestFuelType forestFuelTypeByCode = DatabaseManager.findForestFuelTypeByCode(code);
        Assert.assertEquals(forestFuelTypeByCode.getName(), fuelTypeMap.get(code).getName());
    }

}