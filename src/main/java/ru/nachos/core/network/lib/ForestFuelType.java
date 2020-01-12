package ru.nachos.core.network.lib;

public interface ForestFuelType {
    String getName();

    long getId();

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
