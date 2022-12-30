package git.niklogik.core.fire.lib;

import git.niklogik.core.Id;
import org.locationtech.jts.geom.Coordinate;
import git.niklogik.core.network.lib.PolygonV2;

public interface AgentState {

    Id<Agent> getAgent();

    Id<PolygonV2> getPolygonId();

    double getDistanceFromStart();

    Coordinate getCoordinate();

    double getSpeed();

    Agent getLeftNeighbour();

    Agent getRightNeighbour();

    boolean isHead();

    AgentStatus getStatus();

    int getIterNum();
}
