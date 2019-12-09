package ru.nachos.core.network;

import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkImpl implements Network {

    private String name;
    private NetworkFactory factory;
    private Map<PolygonType, List<PolygonV2>> polygons = new HashMap<>();
    private ForestFuelTypeImpl fuelType;

    NetworkImpl(NetworkFactory factory){
        this.factory = factory;
    }

    @Override
    public NetworkFactory getFactory() { return this.factory;}
    @Override
    public Map<PolygonType, List<PolygonV2>> getPolygones() {return this.polygons;}
    @Override
    public void addPolygonsByType(PolygonType type, List<PolygonV2> polygons) { this.polygons.put(type, polygons); }
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
}
