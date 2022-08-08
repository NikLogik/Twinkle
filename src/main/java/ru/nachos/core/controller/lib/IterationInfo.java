package ru.nachos.core.controller.lib;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import ru.nachos.core.Id;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.FireFactory;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.core.utils.PolygonType;

import java.util.Map;

public interface IterationInfo {
    double getCurTime();

    long getSimTime();

    Network getNetwork();

    AgentMap getAgents();

    double getFireSpeed();

    int getIterNum();

    int getAgentDistance();

    FireSpreadCalculator getCalculator();

    FireFactory getFireFactory();

    GeometryFactory getGeomFactory();

    Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygons();

    int getIterStepTime();

    double getHeadDirection();

    Coordinate getFireCenter();
}
