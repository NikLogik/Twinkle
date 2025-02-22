package git.niklogik.core.network.lib;

import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.db.entities.fire.ContourLine;
import git.niklogik.core.utils.PolygonType;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public interface Network {

    Trip addTrip(UUID id, Trip trip);

    NetworkFactory getFactory();

    Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygones();

    void addPolygonsByType(PolygonType type, Map<Id<PolygonV2>, PolygonV2> polygons);

    void setName(String name);

    String getName();

    ForestFuelType getFuelType();

    void setFuelType(ForestFuelType type);

    TreeMap<Long, ContourLine> getRelief();

    void addAllReliefLines(TreeMap<Long, ContourLine> relief);

    Trip getTrip(UUID agentId);
}
