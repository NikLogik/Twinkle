package ru.nachos.core;

import ru.nachos.core.network.ForestFuelType;
import ru.nachos.db.entity.FuelType;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {


    /*
    Сделано в качестве заглушки для тестирования расчетного слоя, пока не будет создан слой работы в базой данных
     */
    private static Map<Integer, FuelType> fuelTypeMap = new HashMap<>();

    static {
        FuelType pine_sparse = new FuelType(303, "pine_sparse",
                1.7, 2000, 4300, 300, 0.12,
                0.2, 0.3, 15, 1, 0.07);
        FuelType pine_thick = new FuelType(802,"pine_thick",
                0.9, 4920, 4400, 512, 0.70,
                0.1, 0.2, 20, 1, 0.08);
        fuelTypeMap.put(303, pine_sparse);
        fuelTypeMap.put(802, pine_thick);
    }

    public static ForestFuelType findForestFuelTypeByCode(int fuelTypeCode){
        FuelType fuelType = fuelTypeMap.get(fuelTypeCode);
        return new ForestFuelType(fuelType.getFuelTypeId(), fuelType.getName(), fuelType.getMiddleReserve(), fuelType.getUnitArea(), fuelType.getHeat(),
                fuelType.getDensityForestFuel(), fuelType.getDepth(), fuelType.getMoisture(), fuelType.getMaxMoisture(), fuelType.getTreeHeight(),
                fuelType.getCanopyOfWood(), fuelType.getCapacityDensityWood());
    }
}
