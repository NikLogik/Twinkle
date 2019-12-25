package ru.nachos.core.network.lib;

import ru.nachos.core.Id;
import ru.nachos.core.network.ContourLine;
import ru.nachos.core.network.ForestFuelTypeImpl;
import ru.nachos.core.utils.PolygonType;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface Network {

    NetworkFactory getFactory();

    Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygones();

    void addPolygonsByType(PolygonType type, Map<Id<PolygonV2>, PolygonV2> polygons);

    void setName(String name);

    String getName();

    ForestFuelTypeImpl getFuelType();

    void setFuelType(ForestFuelTypeImpl type);

    TreeMap<Id<ContourLine>, ContourLine> getRelief();

    void addAllReliefLines(TreeMap<Id<ContourLine>, ContourLine> relief);
}
