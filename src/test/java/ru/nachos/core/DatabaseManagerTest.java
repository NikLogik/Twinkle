package ru.nachos.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nachos.core.network.ForestFuelType;
import ru.nachos.db.entity.FuelType;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManagerTest {

    public static Map<Integer, FuelType> fuelTypeMap = new HashMap<>();
    private static DatabaseManager manager;


    @Before
    public void init(){
        FuelType pine_sparse = new FuelType(303, "pine_sparse",
                1.7, 2000, 4300, 300, 0.12,
                0.15, 0.3, 15, 10, 0.07);
        FuelType pine_thick = new FuelType(802, "pine_thick",
                0.9, 4920, 4400, 512, 0.70,
                0.1, 0.2, 20, 12, 0.08);
        fuelTypeMap.put(303, pine_sparse);
        fuelTypeMap.put(802, pine_thick);
        manager = new DatabaseManager();
    }

    @Test
    public void findFuelTypeByIdTest(){
        int code = 303;
        ForestFuelType forestFuelTypeByCode = DatabaseManager.findForestFuelTypeByCode(code);
        Assert.assertEquals(forestFuelTypeByCode.getName(), fuelTypeMap.get(code).getName());
    }

}