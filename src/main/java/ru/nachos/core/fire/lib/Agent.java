package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasID;
import ru.nachos.core.network.lib.PolygonV2;

import java.util.Map;

public interface Agent extends HasID {

    AgentState saveState(int iterNum);

    boolean removeState(AgentState state);

    void setDirection(double direction);

    AgentState getStateByIter(int iterNum);

    void setRightNeighbour(Agent twinkle);

    void setLeftNeighbour(Agent twinkle);

    @Override
    Id<Agent> getId();

    Agent getLeftNeighbour();

    Agent getRightNeighbour();

    Coordinate getCoordinate();

    double getSpeed();

    Id<PolygonV2> getPolygonId();

    void setPolygonId(Id<PolygonV2> polygonId);

    double getDirection();

    Map<Integer, AgentState> getStates();

    AgentState getLastState();

    void setCoordinate(Coordinate coord);

    boolean isHead();

    void setHead(boolean head);

    void setSpeed(double speed);

    void setDistanceFromStart(double distanceFromStart);

    double getDistanceFromStart();
}
