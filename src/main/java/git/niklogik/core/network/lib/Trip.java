package git.niklogik.core.network.lib;

import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;

import java.util.LinkedList;

public interface Trip {
    Id<Agent> getAgentId();
    LinkedList<Link> getLinks();
    LinkedList<Node> getNodes();
    boolean addNode(Node node);
    boolean addLink(Link link);
}
