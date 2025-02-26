package git.niklogik.core.controller.lib;

import git.niklogik.core.Id;
import git.niklogik.core.fire.algorithms.FireSpreadCalculator;
import git.niklogik.core.fire.lib.FireFactory;
import git.niklogik.core.network.lib.Network;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.utils.AgentMap;
import git.niklogik.core.utils.PolygonType;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;
import java.util.Map;

public interface IterationInfo {
    double getCurTime();

    long getSimTime();

    Network getNetwork();

    AgentMap getAgents();

    BigDecimal getFireSpeed();

    int getIterNum();

    int getAgentDistance();

    FireSpreadCalculator getCalculator();

    FireFactory getFireFactory();

    Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygons();

    int getIterStepTime();

    double getHeadDirection();

    Coordinate getFireCenter();
}
