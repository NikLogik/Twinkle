package ru.nachos.core.replanning.handlers;

import org.apache.log4j.Logger;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.events.AgentsTooCloseMovedEvent;
import ru.nachos.core.replanning.handlers.lib.AgentTooCloseMovedHandler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AgentTooCloseMovedHandlerImpl implements AgentTooCloseMovedHandler {

    Logger logger = Logger.getLogger(AgentTooCloseMovedHandler.class);

    private List<Id<Agent>> removeCandidates = new LinkedList<>();
    private IterationInfo info;

    @Override
    public void handleEvent(AgentsTooCloseMovedEvent event) {
        this.info = event.getInfo();
        Iterator<Agent> iterator = info.getAgents().iterator();
        Agent source;
        Agent neighbour;
        while (iterator.hasNext()){
            source = iterator.next();
            neighbour = source.getRightNeighbour();
            if (source.getCoordinate().distance(neighbour.getCoordinate()) < info.getAgentDistance()/3){
                removeCandidates.add(source.getId());
            }
        }
        if (!removeCandidates.isEmpty()){
            removeCandidates.forEach(info.getAgents()::setToDisable);
        }
        logger.info("Removed " + removeCandidates.size() + " agents");
    }

    @Override
    public void resetHandler() {
        this.info = null;
        removeCandidates.clear();
    }

    @Override
    public void persistEvents() {

    }
}
