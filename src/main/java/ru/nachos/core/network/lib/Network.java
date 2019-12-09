package ru.nachos.core.network.lib;

import ru.nachos.core.network.ForestFuelTypeImpl;
import ru.nachos.core.utils.PolygonType;

import java.util.List;
import java.util.Map;

public interface Network {

    NetworkFactory getFactory();

    Map<PolygonType, List<PolygonV2>> getPolygones();

    void addPolygonsByType(PolygonType type, List<PolygonV2> polygons);

    void setName(String name);

    String getName();

    ForestFuelTypeImpl getFuelType();

    void setFuelType(ForestFuelTypeImpl type);
}
