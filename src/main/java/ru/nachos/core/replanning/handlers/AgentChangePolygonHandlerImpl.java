package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import org.apache.log4j.Logger;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentStatus;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.events.AgentsChangePolygonEvent;
import ru.nachos.core.replanning.handlers.lib.AgentChangePolygonHandler;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.core.utils.PolygonType;

import java.util.*;

public class AgentChangePolygonHandlerImpl implements AgentChangePolygonHandler {

    Logger logger = Logger.getLogger(AgentChangePolygonHandlerImpl.class);

    private IterationInfo info;
    Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons;
    private Map<Id<Agent>, Agent> addAgents = new HashMap<>();
    private Set<Id<Agent>> stoppedAgents = new TreeSet<>();

    @Override
    public void handleEvent(AgentsChangePolygonEvent event) {
        this.info = event.getInfo();
        this.polygons = info.getPolygons();
        Id<PolygonV2> currentGeom;
        Id<PolygonV2> lastGeom;
        Iterator<Agent> iterator = info.getAgents().iterator();
        Agent agent;
        while (iterator.hasNext()){
            agent = iterator.next();
            if (agent.getStatus().equals(AgentStatus.DISABLED)){
                continue;
            }
            currentGeom = agent.getPolygonId();
            lastGeom = agent.getLastState().getPolygonId();
            if (currentGeom.equals(lastGeom)){
                if (checkMissedIntersection(agent)){
                    agent.setPolygonId(lastGeom);
                }
            } else {
                PolygonType currentPolygonType = null;
                for(PolygonType value : polygons.keySet()) {
                    if(polygons.get(value).containsKey(currentGeom)) {
                        currentPolygonType = value;
                        break;
                    }
                }
                if(currentPolygonType!=null) {
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
                Coordinate crossingPoint = NetworkUtils.findIntersectionPoint(agent.getLastState().getCoordinate(), agent.getCoordinate(), currentPolygon, info.getGeomFactory());
                Coordinate coordinate = GeodeticCalculator.directProblem(crossingPoint, -0.5, agent.getDirection());
                logger.warn("Change behavior for agent ID=" + agent.getId() + ". Set to position " + coordinate + " from position " + agent.getCoordinate() + ", crossing point " + crossingPoint);
                agent.setCoordinate(coordinate);
                Coordinate[] directionLine = GeodeticCalculator.findNearestLine(crossingPoint, currentPolygon);
                double leftDistance = agent.getLastState().getCoordinate().distance(agent.getCoordinate());
                int leftTime = (info.getIterStepTime()/60) - (int)(leftDistance / agent.getSpeed());
                agent.setPolygonId(agent.getLastState().getPolygonId());
                stoppedAgents.add(agent.getId());
                break;
            default:
                checkMissedIntersection(agent);
        }
    }

    private boolean checkMissedIntersection(Agent agent){
        logger.warn("Find intersections for agent ID=" + agent.getId());
        Coordinate last = agent.getLastState().getCoordinate();
        Coordinate current = agent.getCoordinate();
        LineString lineString = info.getGeomFactory().createLineString(new Coordinate[]{last, current});
        List<Geometry> intersections = new ArrayList<>();
        for (Map.Entry<PolygonType, Map<Id<PolygonV2>, PolygonV2>> entry : polygons.entrySet()){
            if (PolygonType.isFireproof(entry.getKey())) {
                for (PolygonV2 polygon : entry.getValue().values()) {
                    if (lineString.intersects(polygon)) {
                        intersections.add(lineString.intersection(polygon));
                    }
                }
            }
        }
        if (!intersections.isEmpty()){
            Coordinate position = null;
            double distance = last.distance(current);
            for (Geometry geom : intersections){
                for (Coordinate coordinate : geom.getCoordinates()){
                    if (last.distance(coordinate) < distance){
                        position = coordinate;
                        distance = last.distance(coordinate);
                    }
                }
            }
            if (position != null){
                logger.warn("Set to agent ID=" + agent.getId() + " new position " + position.toString());
                agent.setCoordinate(position);
                agent.setDistanceFromStart(distance);
                stoppedAgents.add(agent.getId());
                return true;
            }
            else {
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
