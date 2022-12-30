package git.niklogik.core.network;

import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.Node;
import git.niklogik.core.network.lib.Trip;

import java.util.LinkedList;

public class TripImpl implements Trip {
    private Id<Agent> agent;
    private LinkedList<Link> links = new LinkedList<>();
    private LinkedList<Node> nodes = new LinkedList<>();

    TripImpl(Id<Agent> id){
        this.agent = id;
    }

    TripImpl(Id<Agent> id, LinkedList<Node> nodes){
        this.agent = id;
        this.nodes = new LinkedList<>(nodes);
    }

    @Override
    public Id<Agent> getAgentId() {
        return this.agent;
    }

    @Override
    public LinkedList<Link> getLinks() {
        return this.links;
    }

    @Override
    public LinkedList<Node> getNodes() {
        return this.nodes;
    }

    @Override
    public boolean addNode(Node node) {
        return nodes.add(node);
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
