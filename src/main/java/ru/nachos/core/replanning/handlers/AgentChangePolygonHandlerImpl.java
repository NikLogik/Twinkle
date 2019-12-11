package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.events.AgentsChangePolygonEvent;
import ru.nachos.core.replanning.handlers.lib.AgentChangePolygonHandler;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.core.utils.PolygonType;

import java.util.HashMap;
import java.util.Map;

public class AgentChangePolygonHandlerImpl implements AgentChangePolygonHandler {

    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygonesMap;
    private AgentMap casheList;
    private Map<Id<Agent>, Agent> addAgents = new HashMap<>();
    private Fire fire;
    private Controller controller;
    private int iterNum;

    @Override
    public void handleEvent(AgentsChangePolygonEvent event) {
        this.polygonesMap = event.getNetwork().getPolygones();
        this.controller = event.getController();
        this.fire = event.getFire();
        this.iterNum = event.getIterNum();
        casheList = new AgentMap(controller.getAgentsForIter(event.getIterNum()));
        Id<PolygonV2> currentGeom;
        Id<PolygonV2> lastGeom;
        for (Agent agent : casheList.values()){
            currentGeom = agent.getPolygonId();
            lastGeom = agent.getLastState().getPolygonId();
            if (currentGeom.equals(lastGeom)){
                continue;
            } else {
                PolygonType currentPolygonType = null;
                for(PolygonType value : polygonesMap.keySet()) {
                    if(polygonesMap.get(value).containsKey(currentGeom)) {
                        currentPolygonType = value;
                        break;
                    }
                }
                if(currentPolygonType!=null) {
                    agentBehaviorChange(agent, currentPolygonType);
                }
            }
        }
        controller.getFire().getTwinkles().putAll(addAgents);
        controller.getIterationMap().get(iterNum).addAll(addAgents.keySet());
    }

    private void agentBehaviorChange(Agent agent, PolygonType currentPolygonType) {
        switch (currentPolygonType) {
            case WATER:
            case PORT:
            case BEACH:
            case DEFAULT:
                // !!!! В массив возвращаются три координаты: 0-точка пересечения, 1, 2- точки начала и конца отрезка, с которым пересекается траектория движения агента
                PolygonV2 currentPolygon = polygonesMap.get(currentPolygonType).get(agent.getPolygonId());
                Coordinate crossingPoint = NetworkUtils.findIntersectionPoint(agent.getLastState().getCoord(), agent.getCoordinate(), polygonesMap.get(currentPolygonType).get(agent.getPolygonId()));
                agent.setCoordinate(crossingPoint);
                Coordinate[] directionLine = NetworkUtils.findNearestLine(crossingPoint, currentPolygon);
                double leftDistance = agent.getLastState().getCoord().distance(agent.getCoordinate());
                int leftTime = (controller.getConfig().getStepTimeAmount()/60) - (int)(leftDistance / agent.getSpeed());
                setNewOppositeAgents(agent, leftTime, crossingPoint, directionLine);
                agent.setSpeed(0);
                break;
        }
    }

    private void setNewOppositeAgents(Agent agent, int leftTime, Coordinate start, Coordinate[] coordinates){
        // !!!! См. метод agentBehaviorChange
        double direction = GeodeticCalculator.reverseProblem(coordinates[0], coordinates[1]);
        double revDirection = direction > 180.00 ? (direction - 180.00) : (direction + 180.00);
        double fireSpeed = fire.getFireSpeed();
        FireSpreadCalculator calculator = controller.getPreprocessingData().getCalculator();
        // Создаем двух новых агентов, которые будут идти вдоль стороны полигона, от точки, где исходные агент остановился
        Agent dirAgent = fire.getFactory().createTwinkle(Id.createAgentId(agent.getId() + ":direct"));
        dirAgent.setDirection(direction);
        dirAgent.setRightNeighbour(agent);
        dirAgent.setLeftNeighbour(agent.getLeftNeighbour());
        calculator.calculateSpeedOfSpreadWithArbitraryDirection(fireSpeed, dirAgent, fire.getHeadDirection());
        dirAgent.setDistanceFromStart(dirAgent.getSpeed() * leftTime);
        Coordinate position = GeodeticCalculator.directProblem(start, dirAgent.getDistanceFromStart(), direction);
        dirAgent.setCoordinate(position);
        dirAgent.setPolygonId(NetworkUtils.findPolygonByAgentCoords(controller.getNetwork(), position).getId());
        addAgents.put(dirAgent.getId(), dirAgent);
        Agent revAgent = fire.getFactory().createTwinkle(Id.createAgentId(agent.getId() + ":reverse"));
        revAgent.setDirection(revDirection);
        revAgent.setLeftNeighbour(agent);
        revAgent.setRightNeighbour(agent.getRightNeighbour());
        calculator.calculateSpeedOfSpreadWithArbitraryDirection(fireSpeed, revAgent, fire.getHeadDirection());
        revAgent.setDistanceFromStart(revAgent.getSpeed() * leftTime);
        Coordinate revPosition = GeodeticCalculator.directProblem(start, revAgent.getDistanceFromStart(), revDirection);
        revAgent.setCoordinate(revPosition);
        revAgent.setPolygonId(NetworkUtils.findPolygonByAgentCoords(controller.getNetwork(), revPosition).getId());
        addAgents.put(revAgent.getId(), revAgent);
    }

    @Override
    public void resetHandler() {

    }

    @Override
    public void persistEvents() {

    }
}
