package ru.nachos.core.network.lib;

import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.model.fire.ContourLine;

import java.util.Map;
import java.util.TreeMap;

public interface Network {

    Trip addTrip(Id<Agent> id, Trip trip);

    NetworkFactory getFactory();

    Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygones();

    void addPolygonsByType(PolygonType type, Map<Id<PolygonV2>, PolygonV2> polygons);

    void setName(String name);

    String getName();

    ForestFuelType getFuelType();

    void setFuelType(ForestFuelType type);

    TreeMap<Long, ContourLine> getRelief();

    void addAllReliefLines(TreeMap<Long, ContourLine> relief);

    Trip getTrip(Id<Agent> agent);
}
