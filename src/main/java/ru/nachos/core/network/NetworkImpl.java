package ru.nachos.core.network;

import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NetworkImpl implements Network {

    private String name;
    private NetworkFactory factory;
    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons = new HashMap<>();
    private TreeMap<Id<ContourLine>, ContourLine> relief = new TreeMap<>();
    private ForestFuelTypeImpl fuelType;


    NetworkImpl(NetworkFactory factory){
        this.factory = factory;
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
    public ForestFuelTypeImpl getFuelType(){ return this.fuelType; }
    @Override
    public void setFuelType(ForestFuelTypeImpl type){ this.fuelType = type; }
    @Override
    public TreeMap<Id<ContourLine>, ContourLine> getRelief() {
        return relief;
    }
    @Override
    public void addAllReliefLines(TreeMap<Id<ContourLine>, ContourLine> relief) {
        this.relief = relief;
    }
}
