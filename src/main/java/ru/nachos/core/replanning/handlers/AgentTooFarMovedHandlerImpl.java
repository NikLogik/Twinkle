package ru.nachos.core.replanning.handlers;

import ru.nachos.core.Id;
import ru.nachos.core.fire.TwinkleUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.FireFactory;
import ru.nachos.core.replanning.events.AgentsTooFarMovedEvent;
import ru.nachos.core.replanning.handlers.lib.AgentTooFarMovedHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class AgentTooFarMovedHandlerImpl implements AgentTooFarMovedHandler {

    private final String POSTFIX = ":generate";
    private FireFactory factory;
    private Map<Id<Agent>, Agent> casheMap = new LinkedHashMap<>();

    @Override
    public void handleEvent(AgentsTooFarMovedEvent event) {
        factory = event.getFire().getFactory();
        double multiDistance = event.getFireAgentsDistance() * 1.5;
        Agent newAgent;
        for (Agent agent : event.getAgents().values()){
            if (agent.getCoordinate().distance(agent.getRightNeighbour().getCoordinate())>multiDistance){
                newAgent = setMiddleNeighbour(agent, agent.getRightNeighbour(), event.getCounter()+"-"+event.getIterNum());
                newAgent.saveState(event.getIterNum());
                casheMap.put(newAgent.getId(), newAgent);
            }
        }
        event.getAgents().putAll(casheMap);
        casheMap.clear();
    }

    private Agent setMiddleNeighbour(Agent left, Agent right, String numberId){
        Agent newAgent = factory.createTwinkle(Id.createAgentId(numberId + POSTFIX));
        left.setRightNeighbour(newAgent);
        right.setLeftNeighbour(newAgent);
        newAgent.setLeftNeighbour(left);
        newAgent.setRightNeighbour(right);
        TwinkleUtils.calculateMiddleParameters(left, right, newAgent);
        return newAgent;
    }

    @Override
    public void resetHandler() {

    }

    @Override
    public void persistEvents() {

    }
}
