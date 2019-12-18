package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import org.apache.log4j.Logger;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
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
                continue;
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
            case PORT:
            case BEACH:
            case DEFAULT:
                PolygonV2 currentPolygon = polygons.get(currentPolygonType).get(agent.getPolygonId());
                Coordinate crossingPoint = NetworkUtils.findIntersectionPoint(agent.getLastState().getCoordinate(), agent.getCoordinate(), polygons.get(currentPolygonType).get(agent.getPolygonId()));
                agent.setCoordinate(crossingPoint);
                Coordinate[] directionLine = GeodeticCalculator.findNearestLine(crossingPoint, currentPolygon);
                double leftDistance = agent.getLastState().getCoordinate().distance(agent.getCoordinate());
                int leftTime = (info.getIterStepTime()/60) - (int)(leftDistance / agent.getSpeed());
                agent.setPolygonId(agent.getLastState().getPolygonId());
//                setNewOppositeAgents(agent, leftTime, crossingPoint, directionLine);
                stoppedAgents.add(agent.getId());
                break;
        }
    }

    private void setNewOppositeAgents(Agent agent, int leftTime, Coordinate start, Coordinate[] coordinates){
        // !!!! См. метод agentBehaviorChange
        double direction = GeodeticCalculator.reverseProblem(coordinates[0], coordinates[1]);
        double revDirection = direction > 180.00 ? (direction - 180.00) : (direction + 180.00);
        double fireSpeed = info.getFireSpeed();
        FireSpreadCalculator calculator = info.getCalculator();
        // Создаем двух новых агентов, которые будут идти вдоль стороны полигона, от точки, где исходные агент остановился
        Agent dirAgent = info.getFireFactory().createTwinkle(Id.createAgentId(agent.getId() + ":direct"));
        dirAgent.setDirection(direction);
        dirAgent.setRightNeighbour(agent);
        dirAgent.setLeftNeighbour(agent.getLeftNeighbour());
        calculator.calculateSpeedOfSpreadWithArbitraryDirection(fireSpeed, dirAgent, info.getHeadDirection());
        dirAgent.setDistanceFromStart(dirAgent.getSpeed() * leftTime);
        Coordinate position = GeodeticCalculator.directProblem(start, dirAgent.getDistanceFromStart(), direction);
        dirAgent.setCoordinate(position);
        dirAgent.setPolygonId(NetworkUtils.findPolygonByAgentCoords(info.getGeomFactory(), polygons, position).getId());
        dirAgent.setStatus(AgentStatus.ACTIVE);
        addAgents.put(dirAgent.getId(), dirAgent);
        agent.getLeftNeighbour().setRightNeighbour(dirAgent);
        Agent revAgent = info.getFireFactory().createTwinkle(Id.createAgentId(agent.getId() + ":reverse"));
        revAgent.setDirection(revDirection);
        revAgent.setLeftNeighbour(agent);
        revAgent.setRightNeighbour(agent.getRightNeighbour());
        calculator.calculateSpeedOfSpreadWithArbitraryDirection(fireSpeed, revAgent, info.getHeadDirection());
        revAgent.setDistanceFromStart(revAgent.getSpeed() * leftTime);
        Coordinate revPosition = GeodeticCalculator.directProblem(start, revAgent.getDistanceFromStart(), revDirection);
        revAgent.setCoordinate(revPosition);
        revAgent.setPolygonId(NetworkUtils.findPolygonByAgentCoords(info.getGeomFactory(), polygons, revPosition).getId());
        revAgent.setStatus(AgentStatus.ACTIVE);
        addAgents.put(revAgent.getId(), revAgent);
        agent.getRightNeighbour().setLeftNeighbour(revAgent);
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
