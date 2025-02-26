package git.niklogik.core.fire.lib;

import git.niklogik.core.utils.AgentMap;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;
import java.util.UUID;

public interface Fire {

    FireFactory getFactory();

    String getName();

    double getHeadDirection();

    Coordinate getCenterPoint();

    int getPerimeter();

    int getFireClass();

    int getAgentDistance();

    BigDecimal getFireSpeed();

    AgentMap getTwinkles();

    Agent addAgent(Agent agent);

    Agent removeAgent(Agent agent);

    Agent removeAgent(UUID id);

    void setCenterPoint(Coordinate center);

    void setFireSpeed(BigDecimal fireSpeed);
}
