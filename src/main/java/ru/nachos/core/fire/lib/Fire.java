package ru.nachos.core.fire.lib;

import ru.nachos.core.Coord;
import ru.nachos.core.Id;

import java.util.Map;

public interface Fire {

    FireFactory getFactory();

    String getName();

    Coord getCenter();

    int getPerimeter();

    int getAccuracy();

    double getFireSpeed();

    Map<Id<Agent>, Agent> getTwinkles();

    Agent addAgent(Agent agent);

    boolean removeAgent(Agent agent);

    Agent removeAgent(Id<Agent> id);

}
