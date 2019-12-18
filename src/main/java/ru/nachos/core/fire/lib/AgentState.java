package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.PolygonV2;

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
