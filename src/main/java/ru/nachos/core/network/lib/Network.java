package ru.nachos.core.network.lib;

import org.springframework.data.geo.Polygon;
import ru.nachos.core.Id;

import java.util.Map;

public interface Network {

    NetworkFactory getFactory();

    Map<Id<Node>, ? extends Node> getNodes();

    Map<Id<Link>, ? extends Link> getLinks();

    Map<Id<Polygon>, ? extends Polygon> getPolygones();

    void addPolygon(Polygon polygon);

    void addNode(Node node);

    void addLink(Link link);

    Node removeNode(Id<Node> id);

    Link removeLink(Id<Link> id);

    void setName(String name);

    String getName();
}
