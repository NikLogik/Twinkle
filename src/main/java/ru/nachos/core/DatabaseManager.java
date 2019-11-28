package ru.nachos.core;

import ru.nachos.core.network.ForestFuelTypeImpl;
import ru.nachos.core.network.lib.ForestFuelType;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private static Map<Integer, ForestFuelType> fuelTypeMap = new HashMap<>();

    static {
        ForestFuelType pine_sparse = new ForestFuelTypeImpl(303, "pine_sparse",
                1.7, 2000, 4300, 300, 0.12,
                0.2, 0.3, 15, 1, 0.07);
        ForestFuelType pine_thick = new ForestFuelTypeImpl(802,"pine_thick",
                0.9, 4920, 4400, 512, 0.70,
                0.1, 0.2, 20, 1, 0.08);
        ForestFuelType spruce_thick = new ForestFuelTypeImpl(406, "spruce_thick",
                1.0, 2500, 4700, 300, 0.10,
                0.35, 0.5, 15, 1, 0.24);
        ForestFuelType spruce_sparse = new ForestFuelTypeImpl(507, "spruce_sparse",
                0.3, 6000, 4500, 512, 0.10,
                0.15, 0.3, 10, 1, 0.09);
        ForestFuelType greenwood = new ForestFuelTypeImpl(710, "greenwood",
                0.2, 6560, 4400, 512, 0.60,
                0.1, 0.2, 20, 1, 0.08);
        ForestFuelType mineralLine = new ForestFuelTypeImpl(901, "mineral_line",
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0);
        fuelTypeMap.put(303, pine_sparse);
        fuelTypeMap.put(802, pine_thick);
        fuelTypeMap.put(406, spruce_thick);
        fuelTypeMap.put(507, spruce_sparse);
        fuelTypeMap.put(710, greenwood);
        fuelTypeMap.put(901, mineralLine);
    }

    public static ForestFuelType findForestFuelTypeByCode(int fuelTypeCode){
        return fuelTypeMap.get(fuelTypeCode);
    }

    public static Map<Integer, ForestFuelType> getFuelTypeMap() {
        return fuelTypeMap;
    }
}
