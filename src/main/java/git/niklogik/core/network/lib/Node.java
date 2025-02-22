package git.niklogik.core.network.lib;

import git.niklogik.core.controller.lib.HasID;
import org.locationtech.jts.geom.Coordinate;

import java.util.UUID;

public interface Node extends HasID<UUID> {
    @Override
    UUID getId();

    Double getElevation();

    void setInLink(Link link);

    void setOutLink(Link link);

    Link getInLink();

    Link getOutLink();

    Coordinate getCoordinate();

    void setTripTime(int time);

    int getTripTime();
}
