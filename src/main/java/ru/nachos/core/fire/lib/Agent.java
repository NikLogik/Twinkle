package ru.nachos.core.fire.lib;

import ru.nachos.core.Coord;
import ru.nachos.core.Id;

import java.util.List;

public interface Agent {

    Id<Agent> getId();

    Coord getCoord();

    double getSpeed();

    double getDirection();

    List<AgentPlan> getPlans();
}
