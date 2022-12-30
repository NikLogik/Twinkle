package git.niklogik.core.replanning.handlers;

import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentStatus;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.replanning.events.AgentsChangePolygonEvent;
import git.niklogik.core.replanning.lib.Event;
import git.niklogik.core.replanning.lib.EventHandler;
import git.niklogik.core.utils.AgentMap;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.core.utils.GeodeticCalculator;
import git.niklogik.core.utils.PolygonType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AgentChangePolygonHandler implements EventHandler {

    Logger logger = LoggerFactory.getLogger(AgentChangePolygonHandler.class);

    private IterationInfo info;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons;
    private Map<Id<Agent>, Agent> addAgents = new HashMap<>();
    private Set<Id<Agent>> stoppedAgents = new TreeSet<>();

    @Override
    public void handleEvent(Event event) {
        if (event instanceof AgentsChangePolygonEvent changePolygonEvent) {
            handleEvent(changePolygonEvent);
        }
    }

    public void handleEvent(AgentsChangePolygonEvent event) {
        this.info = event.getInfo();
        this.polygons = info.getPolygons();
        Id<PolygonV2> currentGeom;
        Id<PolygonV2> lastGeom;
        Iterator<Agent> iterator = info.getAgents().iterator();
        Agent agent;
        while (iterator.hasNext()) {
            agent = iterator.next();
            if (agent.getStatus().equals(AgentStatus.DISABLED)) {
                continue;
            }
            currentGeom = agent.getPolygonId();
            lastGeom = agent.getLastState().getPolygonId();
            if (currentGeom.equals(lastGeom)) {
                if (checkMissedIntersection(agent)) {
                    agent.setPolygonId(lastGeom);
                }
            } else {
                PolygonType currentPolygonType = null;
                for (PolygonType value : polygons.keySet()) {
                    if (polygons.get(value).containsKey(currentGeom)) {
                        currentPolygonType = value;
                        break;
                    }
                }
                if (currentPolygonType != null) {
                    agentBehaviorChange(agent, currentPolygonType);
                }
            }
        }
        if (!addAgents.isEmpty()) {
            info.getAgents().merge(new AgentMap(addAgents));
        }
        stoppedAgents.forEach(info.getAgents()::setToStopped);
        logger.info("Finished to find the new locations for agents.");
    }

    private void agentBehaviorChange(Agent agent, PolygonType currentPolygonType) {
        switch (currentPolygonType) {
            case WATER:
            case BEACH:
            case DEFAULT:
                PolygonV2 currentPolygon = polygons.get(currentPolygonType).get(agent.getPolygonId());
                Coordinate crossingPoint = NetworkUtils.findIntersectionPoint(agent.getLastState().getCoordinate(), agent.getCoordinate(), currentPolygon);
                Coordinate coordinate = GeodeticCalculator.directProblem(crossingPoint, -0.5, agent.getDirection());
                logger.warn("Change behavior for agent ID=" + agent.getId() + ". Set to position " + coordinate + " from position " + agent.getCoordinate() + ", crossing point " + crossingPoint);
                agent.setCoordinate(coordinate);
                agent.setPolygonId(agent.getLastState().getPolygonId());
                stoppedAgents.add(agent.getId());
                break;
            default:
                checkMissedIntersection(agent);
        }
    }

    private boolean checkMissedIntersection(Agent agent) {
        logger.warn("Find intersections for agent ID=" + agent.getId());
        Coordinate last = agent.getLastState().getCoordinate();
        Coordinate current = agent.getCoordinate();
        LineString lineString = geometryFactory.createLineString(new Coordinate[]{ last, current });
        List<Geometry> intersections = new ArrayList<>();
        for (Map.Entry<PolygonType, Map<Id<PolygonV2>, PolygonV2>> entry : polygons.entrySet()) {
            if (PolygonType.isFireproof(entry.getKey())) {
                for (PolygonV2 polygon : entry.getValue().values()) {
                    if (lineString.intersects(polygon)) {
                        intersections.add(lineString.intersection(polygon));
                    }
                }
            }
        }
        if (!intersections.isEmpty()) {
            Coordinate position = null;
            double distance = last.distance(current);
            for (Geometry geom : intersections) {
                for (Coordinate coordinate : geom.getCoordinates()) {
                    if (last.distance(coordinate) < distance) {
                        position = coordinate;
                        distance = last.distance(coordinate);
                    }
                }
            }
            if (position != null) {
                logger.warn("Set to agent ID=" + agent.getId() + " new position " + position.toString());
                agent.setCoordinate(position);
                agent.setDistanceFromStart(distance);
                stoppedAgents.add(agent.getId());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void resetHandler() {
        addAgents.clear();
        stoppedAgents.clear();
    }

    @Override
    public void persistEvents() {

    }
}
