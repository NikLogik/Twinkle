package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import org.apache.log4j.Logger;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.events.AgentsTooCloseMovedEvent;
import ru.nachos.core.replanning.handlers.lib.AgentTooCloseMovedHandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AgentTooCloseMovedHandlerImpl implements AgentTooCloseMovedHandler {

    Logger logger = Logger.getLogger(AgentTooCloseMovedHandler.class);

    private Set<Id<Agent>> removeAgents = new HashSet<>();

    @Override
    public void handleEvent(AgentsTooCloseMovedEvent event) {
        Map<Id<Agent>, Agent> agentsForIter = event.getController().getAgentsForIter(event.getIterNum());
        Coordinate source;
        Coordinate neighbour;
        for (Agent agent : agentsForIter.values()){
            source = agent.getCoordinate();
            neighbour = agent.getRightNeighbour().getCoordinate();
            if (source.distance(neighbour) < event.getMinDistance()){
                removeAgents.add(agent.getId());
            } else if (source.distance(agent.getRightNeighbour().getRightNeighbour().getCoordinate()) < source.distance(neighbour)){
                removeAgents.add(agent.getRightNeighbour().getId());
            }
        }
        removeAgents.forEach(event.getController().getAgentsForIter(event.getIterNum())::remove);
        logger.info("Removed " + removeAgents.size() + " agents");
    }

    @Override
    public void resetHandler() { removeAgents.clear(); }

    @Override
    public void persistEvents() {

    }
}
