package ru.nachos.core.network;

import ru.nachos.core.Id;
import ru.nachos.core.network.lib.*;

import java.util.HashMap;
import java.util.Map;

public class NetworkImpl implements Network {

    private String name;
    private NetworkFactory factory;
    private Map<Id<Node>, Node> nodes = new HashMap<>();
    private Map<Id<Link>, Link> links = new HashMap<>();
    private Map<Id<PolygonV2>, PolygonV2> polygons = new HashMap<>();
    private ForestFuelTypeImpl fuelType;

    NetworkImpl(NetworkFactory factory){
        this.factory = factory;
    }

    @Override
    public NetworkFactory getFactory() { return this.factory;}
    @Override
    public Map<Id<Node>, ? extends Node> getNodes() {return this.nodes;}
    @Override
    public Map<Id<Link>, ? extends Link> getLinks() {return this.links;}
    @Override
    public Map<Id<PolygonV2>, ? extends PolygonV2> getPolygones() {return this.polygons;}
    @Override
    public void addPolygon(PolygonV2 polygon) {
        polygons.put(polygon.getId(), polygon);
    }
    @Override
    public void addNode(Node node) {nodes.put(node.getId(), node);}
    @Override
    public void addLink(Link link) {links.put(link.getId(), link);}
    @Override
    public Node removeNode(Id<Node> id) {return nodes.remove(id);}
    @Override
    public Link removeLink(Id<Link> id) {
        return links.remove(id);
    }
    @Override
    public PolygonV2 removePolygon(Id<PolygonV2> id) { return polygons.remove(id); }
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
