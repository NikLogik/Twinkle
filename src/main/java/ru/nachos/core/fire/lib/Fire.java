package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.utils.AgentMap;

public interface Fire {

    FireFactory getFactory();

    String getName();

    double getHeadDirection();

    Coordinate getCenterPoint();

    int getPerimeter();

    int getFireClass();

    int getAgentDistance();

    double getFireSpeed();

    AgentMap getTwinkles();

    Agent addAgent(Agent agent);

    Agent removeAgent(Agent agent);

    Agent removeAgent(Id<Agent> id);

    void setCenterPoint(Coordinate center);

    void setFireSpeed(double fireSpeed);

    class Definitions{
        public static final String POST_FIX = ":twinkle";
    }
}
