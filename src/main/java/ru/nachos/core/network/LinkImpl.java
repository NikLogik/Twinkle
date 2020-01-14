package ru.nachos.core.network;

import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Link;
import ru.nachos.core.network.lib.Node;

public class LinkImpl implements Link {

    private Id<Link> id;
    private Node fromNode;
    private Node toNode;
    private double linkSpeed;
    private int timeFlow;

    LinkImpl(Id<Link> id, Node fromNode, Node toNode, double linkSpeed, int timeFlow){
        this.id = id;
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.linkSpeed = linkSpeed;
        this.timeFlow = timeFlow;
    }

    @Override
    public Id<Link> getId() {
        return this.id;
    }

    @Override
    public Node getToNode() {
        return this.toNode;
    }

    @Override
    public Node getFromNode() {
        return this.fromNode;
    }

    @Override
    public void setToNode(Node node) {
        this.toNode = node;
    }

    @Override
    public void setFromNode(Node node) {
        this.fromNode = node;
    }

    @Override
    public double getTimeFlow() {
        return this.timeFlow;
    }

    @Override
    public double getLinkSpeed() {
        return this.linkSpeed;
    }
    @Override
    public void setLinkSpeed(double kRelief) {
        this.linkSpeed = kRelief;
    }
    @Override
    public void setTimeFlow(int timeFlow) {
        this.timeFlow = timeFlow;
    }
}
