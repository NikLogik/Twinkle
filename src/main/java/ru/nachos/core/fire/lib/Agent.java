package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Point;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasID;

import java.util.List;

public interface Agent extends HasID {

    void setRightNeighbour(Agent twinkle);

    void setLeftNeighbour(Agent twinkle);

    @Override
    Id<Agent> getId();

    Agent getLeftNeighbour();

    Agent getRightNeighbour();

    Point getPoint();

    double getSpeed();

    double getDirection();

    List<AgentState> getPlans();

    AgentState getLastState();

    boolean isHead();

    void setHead(boolean head);
}
