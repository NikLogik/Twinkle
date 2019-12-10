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
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.core.utils.PolygonType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AgentChangePolygonHandlerImpl implements AgentChangePolygonHandler {

    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygonesMap;
    private List<Agent> casheList;
    private Fire fire;
    private Controller controller;
    private int iterNum;

    @Override
    public void handleEvent(AgentsChangePolygonEvent event) {
        this.polygonesMap = event.getNetwork().getPolygones();
        this.controller = event.getController();
        this.fire = event.getFire();
        this.iterNum = event.getIterNum();
        casheList = new LinkedList<>(event.getFire().getTwinkles().values());
        Id<PolygonV2> currentGeom;
        Id<PolygonV2> lastGeom;
        for (Agent agent : casheList){
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
    }

    private void agentBehaviorChange(Agent agent, PolygonType currentPolygon) {
        switch (currentPolygon) {
            case WATER:
            case PORT:
            case BEACH:
            case DEFAULT:
                agent.setSpeed(0);
                // !!!! В массив возвращаются три координаты: 0-точка пересечения, 1, 2- точки начала и конца отрезка, с которым пересекается траектория движения агента
                Coordinate[] crossingPoints = GeodeticCalculator.findCrossPointWithPolygon(agent.getLastState().getCoord(), agent.getCoordinate(), polygonesMap.get(currentPolygon).get(agent.getPolygonId()));
                agent.setCoordinate(crossingPoints[0]);
                double leftDistance = agent.getLastState().getCoord().distance(agent.getCoordinate());
                int leftTime = controller.getConfig().getStepTimeAmount() - (int)(agent.getSpeed() / leftDistance);
                setNewOppositeAgents(agent, leftTime, crossingPoints);
                break;
        }
    }

    private void setNewOppositeAgents(Agent agent, int leftTime, Coordinate[] coordinates){
        // !!!! См. метод agentBehaviorChange
        double direction = GeodeticCalculator.reverseProblem(coordinates[1], coordinates[2]);
        double revDirection = direction > 180.00 ? (direction - 180.00) : (direction + 180.00);
        double fireSpeed = fire.getFireSpeed();
        FireSpreadCalculator calculator = controller.getPreprocessingData().getCalculator();
        // Создаем двух новых агентов, которые будут идти вдоль стороны полигона, от точки, где исходные агент остановился
        Agent dirAgent = fire.getFactory().createTwinkle(Id.createAgentId(agent.getId() + ":direct"));
        dirAgent.setDirection(direction);
        dirAgent.setRightNeighbour(agent);
        dirAgent.setLeftNeighbour(agent.getLeftNeighbour());
        calculator.calculateSpeedOfSpreadWithArbitraryDirection(fireSpeed, agent, direction);
        dirAgent.setDistanceFromStart(dirAgent.getSpeed() * leftTime);
        Coordinate position = GeodeticCalculator.directProblem(coordinates[0], dirAgent.getDistanceFromStart(), dirAgent.getDirection());
        dirAgent.setCoordinate(position);
        dirAgent.setPolygonId(NetworkUtils.findPolygonByAgentCoords(controller.getNetwork(), dirAgent.getCoordinate()).getId());


        Agent revAgent = fire.getFactory().createTwinkle(Id.createAgentId(agent.getId() + ":reverse"));
        revAgent.setDirection(revDirection);
        revAgent.setLeftNeighbour(agent);
        revAgent.setRightNeighbour(agent.getRightNeighbour());
        calculator.calculateSpeedOfSpreadWithArbitraryDirection(fireSpeed, agent, fire.getHeadDirection());
        revAgent.setDistanceFromStart(revAgent.getSpeed() * leftTime);
        Coordinate revPosition = GeodeticCalculator.directProblem(coordinates[0], revAgent.getDistanceFromStart(), revAgent.getDirection());
        revAgent.setCoordinate(revPosition);
        revAgent.setPolygonId(NetworkUtils.findPolygonByAgentCoords(controller.getNetwork(), revAgent.getCoordinate()).getId());
        fire.getTwinkles().put(dirAgent.getId(), dirAgent);
        fire.getTwinkles().put(revAgent.getId(), revAgent);
    }

    @Override
    public void resetHandler() {

    }

    @Override
    public void persistEvents() {

    }
}
