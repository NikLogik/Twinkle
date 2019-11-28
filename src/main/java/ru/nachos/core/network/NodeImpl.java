package ru.nachos.core.network;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Link;
import ru.nachos.core.network.lib.Node;

import java.util.Map;

class NodeImpl implements Node {

    private Id<Node> id;
    private Map<Id<Link>, Link> inLinks;
    private Map<Id<Link>, Link> outLinks;
    private Coordinate coordinate;

    NodeImpl(Id<Node> id){
        this.id = id;
    }

    NodeImpl(Id<Node> id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    @Override
    public Id<Node> getId() {return this.id;}

    @Override
    public boolean addInLink(Link link) {return this.inLinks.put(link.getId(), link) != null;}

    @Override
    public boolean addOutLink(Link link) {return this.outLinks.put(link.getId(), link) != null;}

    @Override
    public Link removeInLink(Id<Link> link) {return this.inLinks.remove(link);}

    @Override
    public Link removeOutLink(Id<Link> link) {return this.outLinks.remove(link);}

    @Override
    public Map<Id<Link>, Link> getInLinks() {return this.inLinks;}

    @Override
    public Map<Id<Link>, Link> getOutLinks() {return this.outLinks;}

    @Override
    public void setCoordinate(Coordinate coord) { this.coordinate = coord;}

    @Override
    public Coordinate getCoordinate() {return this.coordinate;}
}
