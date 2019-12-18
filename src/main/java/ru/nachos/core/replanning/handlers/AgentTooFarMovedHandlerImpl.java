package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.TwinkleUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentStatus;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.events.AgentsTooFarMovedEvent;
import ru.nachos.core.replanning.handlers.lib.AgentTooFarMovedHandler;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.core.utils.PolygonType;

import java.util.*;

public class AgentTooFarMovedHandlerImpl implements AgentTooFarMovedHandler {

    private final String POSTFIX = ":twinkle";
    private IterationInfo info;
    private Map<Id<Agent>, Agent> cacheActive = new LinkedHashMap<>();
    private Set<Id<Agent>> cacheStopped = new TreeSet<>();
    private Polygon firePolygon;

    @Override
    public void handleEvent(AgentsTooFarMovedEvent event) {
        this.info = event.getInfo();
        this.firePolygon = FireUtils.getPolygonFromAgentMap(info.getAgents(), info.getGeomFactory());
        double multiDistance = info.getAgentDistance() * 1.5;
        Agent newAgent;
        Iterator<Agent> iterator = info.getAgents().iterator();
        while (iterator.hasNext()){
            Agent agent = iterator.next();
            if (agent.getCoordinate().distance(agent.getRightNeighbour().getCoordinate()) > multiDistance) {
                newAgent = setMiddleNeighbour(agent, agent.getRightNeighbour(), event.getCounter() + "-" + event.getIterNum());
                newAgent.saveState(info.getIterNum());
                cacheActive.put(newAgent.getId(), newAgent);
            }
        }
        cacheActive.forEach(info.getAgents()::put);
        if (!cacheStopped.isEmpty()){
            cacheStopped.forEach(info.getAgents()::setToStopped);
        }
        cacheActive.clear();
    }

    private Agent setMiddleNeighbour(Agent left, Agent right, String numberId){
        Agent newAgent = info.getFireFactory().createTwinkle(Id.createAgentId(numberId + POSTFIX));
        left.setRightNeighbour(newAgent);
        right.setLeftNeighbour(newAgent);
        newAgent.setLeftNeighbour(left);
        newAgent.setRightNeighbour(right);
        TwinkleUtils.calculateMiddleParameters(left, right, newAgent);
        PolygonV2 geometry = NetworkUtils.findPolygonByAgentCoords(info.getGeomFactory(), info.getPolygons(), newAgent.getCoordinate());
        if (PolygonType.isFireproof(geometry.getPolygonType())) {
            Coordinate[] nearestLine = GeodeticCalculator.findNearestLine(newAgent.getCoordinate(), geometry);
            //находим оринетацию линии в координатной системе (дирекционный угол к линии)
            double direction = GeodeticCalculator.reverseProblem(nearestLine[0], nearestLine[1]);
            Coordinate coordinate = GeodeticCalculator.closestPoint(nearestLine[0], nearestLine[1], newAgent.getCoordinate(), direction);
            newAgent.setCoordinate(coordinate);
            cacheStopped.add(newAgent.getId());
            newAgent.setPolygonId(left.getPolygonId());
        } else {
            newAgent.setPolygonId(geometry.getId());
        }
        newAgent.setStatus(AgentStatus.ACTIVE);
        return newAgent;
    }

    private Coordinate[] insideFirefront(Polygon polygon){
        Coordinate[] coordinates = polygon.getExteriorRing().getCoordinates();
        LinkedList<Coordinate> inside = new LinkedList<>();
        for (int i=0; i<coordinates.length; i++){
            Point point = info.getGeomFactory().createPoint(coordinates[i]);
            if (point.within(firePolygon)){
                inside.add(point.getCoordinate());
            }
        }
        return inside.toArray(new Coordinate[0]);
    }

    private boolean isWithin(Coordinate p1){
        return info.getGeomFactory().createPoint(p1).within(firePolygon);
    }

    @Override
    public void resetHandler() {
        this.info = null;
        cacheActive.clear();
    }

    @Override
    public void persistEvents() {

    }
}
