package ru.nachos.core.network;

import org.locationtech.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Link;
import ru.nachos.core.network.lib.Node;

public class NodeImpl implements Node {
    private Id<Node> id;
    private Link inLink;
    private Link outLink;
    private double elevation;
    private Coordinate coordinate;
    private int tripTime;

    NodeImpl(Id<Node> id, double elevation, Coordinate coordinate){
        this.id = id;
        this.elevation = elevation;
        this.coordinate = coordinate;
        this.tripTime = 0;
    }

    @Override
    public Id<Node> getId() {
        return this.id;
    }

    @Override
    public double getElevation() {
        return this.elevation;
    }

    @Override
    public void setInLink(Link link) {
        this.inLink = link;
    }

    @Override
    public void setOutLink(Link link) {
        this.outLink = link;
    }

    @Override
    public Link getInLink() {
        return this.inLink;
    }

    @Override
    public Link getOutLink() {
        return this.outLink;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }
    @Override
    public void setTripTime(int time){
        this.tripTime = time;
    }

    @Override
    public int getTripTime(){
        return this.tripTime;
    }
}
