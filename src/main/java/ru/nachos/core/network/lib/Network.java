package ru.nachos.core.network.lib;

import ru.nachos.core.Id;
import ru.nachos.core.network.ForestFuelType;

import java.util.Map;

public interface Network {

    NetworkFactory getFactory();

    Map<Id<Node>, ? extends Node> getNodes();

    Map<Id<Link>, ? extends Link> getLinks();

    Map<Id<PolygonV2>, ? extends PolygonV2> getPolygones();

    void addPolygon(PolygonV2 polygon);

    void addNode(Node node);

    void addLink(Link link);

    Node removeNode(Id<Node> id);

    Link removeLink(Id<Link> id);

    PolygonV2 removePolygon(Id<PolygonV2> id);

    void setName(String name);

    String getName();

    ForestFuelType getFuelType();

    void setFuelType(ForestFuelType type);


}
