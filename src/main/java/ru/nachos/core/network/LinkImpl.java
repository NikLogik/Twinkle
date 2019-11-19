package ru.nachos.core.network;

import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Link;
import ru.nachos.core.network.lib.Node;

class LinkImpl implements Link {

    private Id<Link> id;
    private Node fromNode;
    private Node toNode;
    private double length;

    public LinkImpl(Id<Link> id, Node fromNode, Node toNode) {
        this.id = id;
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.length = fromNode.getCoordinate().distance(toNode.getCoordinate());
    }

    @Override
    public Id<Link> getId() {return this.id;}

    @Override
    public Node getToNode() {return null;}

    @Override
    public Node getFromNode() {return null;}

    @Override
    public boolean setToNode(Node node) {return false;}

    @Override
    public boolean setFromNode(Node node) {return false;}

    @Override
    public double getLength() {return this.length;}
}
