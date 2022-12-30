package git.niklogik.core.network.lib;

import git.niklogik.core.Id;
import git.niklogik.core.controller.lib.HasID;

public interface Link extends HasID {
    @Override
    Id<Link> getId();

    Node getToNode();

    Node getFromNode();

    void setToNode(Node node);

    void setFromNode(Node node);

    double getTimeFlow();

    double getLinkSpeed();

    void setLinkSpeed(double kRelief);

    void setTimeFlow(int timeFlow);
}
