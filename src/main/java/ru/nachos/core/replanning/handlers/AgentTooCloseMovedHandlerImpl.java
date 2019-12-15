package ru.nachos.core.replanning.handlers;

import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.events.AgentsTooCloseMovedEvent;
import ru.nachos.core.replanning.handlers.lib.AgentTooCloseMovedHandler;
import ru.nachos.core.utils.AgentMap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AgentTooCloseMovedHandlerImpl implements AgentTooCloseMovedHandler {

//    Logger logger = Logger.getLogger(AgentTooCloseMovedHandler.class);

    List<Id<Agent>> removeCandidates;

    @Override
    public void handleEvent(AgentsTooCloseMovedEvent event) {
        removeCandidates = new LinkedList<>();
        AgentMap map = event.getAgents();
        Iterator<Agent> iterator = map.iterator();
        Agent source;
        Agent neighbour;
        while (iterator.hasNext()){
            source = iterator.next();
            neighbour = source.getRightNeighbour();
            if (source.getCoordinate().distance(neighbour.getCoordinate()) < event.getMinDistance()){
                removeCandidates.add(source.getId());
            }
        }
        if (!removeCandidates.isEmpty()){
            Id<Agent> agentId = removeCandidates.get(0);
            map.remove(agentId);
        }
//        logger.info("Removed " + removeCandidates.size() + " agents");
    }

    @Override
    public void resetHandler() { removeCandidates.clear(); }

    @Override
    public void persistEvents() {

    }
}
