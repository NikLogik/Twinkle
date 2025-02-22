package git.niklogik.core.fire.lib;

import git.niklogik.core.Id;
import org.locationtech.jts.geom.Coordinate;
import git.niklogik.core.network.lib.PolygonV2;

import java.math.BigDecimal;
import java.util.UUID;

public interface AgentState {

    UUID getAgent();

    Id<PolygonV2> getPolygonId();

    BigDecimal getDistanceFromStart();

    Coordinate getCoordinate();

    BigDecimal getSpeed();

    Agent getLeftNeighbour();

    Agent getRightNeighbour();

    boolean isHead();

    AgentStatus getStatus();

    int getIterNum();
}
