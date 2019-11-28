package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;

import java.util.Map;

public interface Fire {

    FireFactory getFactory();

    String getName();

    Coordinate getCenterPoint();

    int getPerimeter();

    int getFireClass();

    int getAgentDistance();

    double getFireSpeed();

    Map<Id<Agent>, Agent> getTwinkles();

    Agent addAgent(Agent agent);

    boolean removeAgent(Agent agent);

    Agent removeAgent(Id<Agent> id);

    void setCenterPoint(Coordinate center);

    void setFireSpeed(double fireSpeed);

    class Definitions{
        public static final String POST_FIX = ":twinkle";
    }
}
