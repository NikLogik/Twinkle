package git.niklogik.core.network;

import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.Node;
import git.niklogik.core.network.lib.Trip;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.UUID;

@RequiredArgsConstructor
public class TripImpl implements Trip {
    private final UUID agent;
    private final LinkedList<Link> links = new LinkedList<>();
    private final LinkedList<Node> nodes = new LinkedList<>();

    TripImpl(UUID agent, LinkedList<Node> links) {
        this(agent);
        this.nodes.addAll(links);
    }

    @Override
    public UUID getAgentId() {
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
