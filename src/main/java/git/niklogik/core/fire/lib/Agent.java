package git.niklogik.core.fire.lib;

import git.niklogik.core.Id;
import git.niklogik.core.controller.lib.HasID;
import git.niklogik.core.network.lib.PolygonV2;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public interface Agent extends HasID<UUID> {

    AgentState saveState(int iterNum);

    boolean removeState(AgentState state);

    void setDirection(BigDecimal direction);

    AgentState getStateByIter(int iterNum);

    void setRightNeighbour(Agent twinkle);

    void setLeftNeighbour(Agent twinkle);

    @Override
    UUID getId();

    Map<Integer, AgentState> getPlanList();

    Agent getLeftNeighbour();

    Agent getRightNeighbour();

    Coordinate getCoordinate();

    BigDecimal getSpeed();

    Id<PolygonV2> getPolygonId();

    void setPolygonId(Id<PolygonV2> polygonId);

    BigDecimal getDirection();

    AgentState getLastState();

    void setCoordinate(Coordinate coord);

    boolean isHead();

    void setHead(boolean head);

    void setSpeed(BigDecimal speed);

    void setDistanceFromStart(double distanceFromStart);

    BigDecimal getDistanceFromStart();

    boolean isStopped();

    void setStopped(boolean stopped);

    AgentStatus getStatus();

    void setStatus(AgentStatus status);

}
