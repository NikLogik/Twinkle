package git.niklogik.core.fire.lib;

import git.niklogik.core.utils.AgentMap;
import org.locationtech.jts.geom.Coordinate;

import java.util.UUID;

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

    Agent removeAgent(UUID id);

    void setCenterPoint(Coordinate center);

    void setFireSpeed(double fireSpeed);

    class Definitions{
        public static final String POST_FIX = ":twinkle";
    }
}
