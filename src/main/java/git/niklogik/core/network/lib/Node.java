package git.niklogik.core.network.lib;

import git.niklogik.core.Id;
import git.niklogik.core.controller.lib.HasID;
import org.locationtech.jts.geom.Coordinate;

public interface Node extends HasID {
    @Override
    Id<Node> getId();

    double getElevation();

    void setInLink(Link link);

    void setOutLink(Link link);

    Link getInLink();

    Link getOutLink();

    void setCoordinate(Coordinate coordinate);

    Coordinate getCoordinate();

    void setTripTime(int time);

    int getTripTime();
}
