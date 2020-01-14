package ru.nachos.core.network.lib;

import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;

import java.util.LinkedList;

public interface Trip {
    Id<Agent> getAgentId();
    LinkedList<Link> getLinks();
    LinkedList<Node> getNodes();
    boolean addNode(Node node);
    boolean addLink(Link link);
}
