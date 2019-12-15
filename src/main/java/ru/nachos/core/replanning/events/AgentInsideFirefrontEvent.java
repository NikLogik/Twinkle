package ru.nachos.core.replanning.events;

import com.vividsolutions.jts.geom.GeometryFactory;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.lib.Event;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.core.utils.PolygonType;

import java.util.Map;

public class AgentInsideFirefrontEvent extends Event {


    private final GeometryFactory geoFactory;
    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons;
    private AgentMap agents;

    public AgentInsideFirefrontEvent(int iterNum, GeometryFactory geoFactory, AgentMap agents, Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons) {
        super(iterNum);
        this.geoFactory = geoFactory;
        this.agents = agents;
        this.polygons = polygons;
    }

    @Override
    public String getEventType() { return "inside_firefront_event"; }

    public GeometryFactory getGeoFactory() { return geoFactory; }

    public Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygons() { return polygons; }

    public AgentMap getAgents() { return agents; }
}

