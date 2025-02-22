package git.niklogik.core.network;

import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.network.lib.ForestFuelType;
import git.niklogik.core.network.lib.Network;
import git.niklogik.core.network.lib.NetworkFactory;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.network.lib.Trip;
import git.niklogik.core.utils.PolygonType;
import git.niklogik.db.entities.fire.ContourLine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class NetworkImpl implements Network {

    private String name;
    private NetworkFactory factory;
    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons = new HashMap<>();
    private TreeMap<Long, ContourLine> relief = new TreeMap<>();
    private HashMap<UUID, Trip> trips = new HashMap<>();
    private ForestFuelType fuelType;

    NetworkImpl(NetworkFactory factory){
        this.factory = factory;
    }

    @Override
    public Trip addTrip(UUID id, Trip trip){
        return trips.put(id, trip);
    }
    @Override
    public NetworkFactory getFactory() { return this.factory;}
    @Override
    public Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygones() {return this.polygons;}
    @Override
    public void addPolygonsByType(PolygonType type, Map<Id<PolygonV2>, PolygonV2> polygons) { this.polygons.put(type, polygons); }
    @Override
    public void setName(String name) {this.name = name;}
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public ForestFuelType getFuelType(){ return this.fuelType; }
    @Override
    public void setFuelType(ForestFuelType type){ this.fuelType = type; }
    @Override
    public TreeMap<Long, ContourLine> getRelief() {
        return relief;
    }
    @Override
    public void addAllReliefLines(TreeMap<Long, ContourLine> relief) {
        this.relief = relief;
    }
    @Override
    public Trip getTrip(UUID agentId) {
        return trips.get(agentId);
    }
}
