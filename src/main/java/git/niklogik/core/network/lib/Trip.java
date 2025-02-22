package git.niklogik.core.network.lib;

import java.util.LinkedList;
import java.util.UUID;

public interface Trip {
    UUID getAgentId();
    LinkedList<Link> getLinks();
    LinkedList<Node> getNodes();
    boolean addNode(Node node);
    boolean addLink(Link link);
}
