package ru.nachos.core.replanning.handlers;

import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.events.AgentsChangePolygonEvent;
import ru.nachos.core.replanning.handlers.lib.AgentChangePolygonHandler;
import ru.nachos.core.utils.PolygonType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AgentChangePolygonHandlerImpl implements AgentChangePolygonHandler {

    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygonesMap;
    private List<Agent> casheList;

    @Override
    public void handleEvent(AgentsChangePolygonEvent event) {
        polygonesMap = event.getNetwork().getPolygones();
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
                for(PolygonType value : PolygonType.values()) {
                    if(polygonesMap.get(value).containsKey(currentGeom)) {
                        currentPolygonType = value;
                    }
                }

                if(currentPolygonType!=null) {
                    agentBehaviorChange(agent, currentPolygonType);
                }

            }
        }
    }

    private void agentBehaviorChange(Agent agent, PolygonType currentPolygonType) {
        switch (currentPolygonType) {
            case WATER:
                agent.setSpeed(0);
                break;
        }
    }

    @Override
    public void resetHandler() {

    }

    @Override
    public void persistEvents() {

    }
}
