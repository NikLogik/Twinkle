package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasID;

import java.util.Map;

public interface Agent extends HasID {

    AgentStateV2 saveState(int iterNum);

    boolean removeState(AgentStateV2 state);

    void setDirection(double direction);

    void setRightNeighbour(Agent twinkle);

    void setLeftNeighbour(Agent twinkle);

    @Override
    Id<Agent> getId();

    Agent getLeftNeighbour();

    Agent getRightNeighbour();

    Coordinate getCoordinate();

    double getSpeed();

    double getDirection();

    Map<Integer, AgentStateV2> getStates();

    AgentStateV2 getLastState();

    void setPoint(Coordinate coord);

    boolean isHead();

    void setHead(boolean head);

    void setSpeed(double speed);

    void setDistanceFromStart(double distanceFromStart);

    double getDistanceFromStart();
}
