package ru.nachos.core.network.lib;

import ru.nachos.core.Id;
import ru.nachos.core.network.ForestFuelTypeImpl;

public interface ForestFuelType {
    String getName();

    Id<ForestFuelTypeImpl> getId();

    double getMiddleReserve();

    int getSpecificArea();

    int getHeat();

    int getDensityForestFuel();

    double getDepth();

    double getMoisture();

    double getMaxMoisture();

    double getMineralMatter();

    double getMineralConsistency();

    int getTreeHeight();

    double getCanopyOfWood();

    double getCapacityDensityWood();
}
