package ru.nachos.core.controller.lib;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import ru.nachos.core.Id;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.FireFactory;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.core.utils.PolygonType;

import java.util.Map;

public interface IterationInfo {
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
