package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;

public interface AgentState {

    Id<Agent> getAgent();

    double getDistanceFromStart();

    Coordinate getCoord();

    double getSpeed();

    Agent getLeftNeighbour();

    Agent getRightNeighbour();

    boolean isHead();

    int getIterNum();
}
