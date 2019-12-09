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

    private Map<PolygonType, List<PolygonV2>> polygonesMap;
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
            }
            
        }
    }

    @Override
    public void resetHandler() {

    }

    @Override
    public void persistEvents() {

    }
}
