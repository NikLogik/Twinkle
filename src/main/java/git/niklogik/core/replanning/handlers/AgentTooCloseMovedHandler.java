package git.niklogik.core.replanning.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import git.niklogik.core.Id;
import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.replanning.events.AgentsTooCloseMovedEvent;
import git.niklogik.core.replanning.lib.Event;
import git.niklogik.core.replanning.lib.EventHandler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AgentTooCloseMovedHandler implements EventHandler {

    Logger logger = LoggerFactory.getLogger(AgentTooCloseMovedHandler.class);

    private List<Id<Agent>> removeCandidates = new LinkedList<>();
    private IterationInfo info;

    @Override
    public void handleEvent(Event event) {
        if (event instanceof AgentsTooCloseMovedEvent tooCloseMovedEvent){
            handleEvent(tooCloseMovedEvent);
        }
    }

    public void handleEvent(AgentsTooCloseMovedEvent event) {
        this.info = event.getInfo();
        Iterator<Agent> iterator = info.getAgents().iterator();
        Agent source;
        Agent neighbour;
        while (iterator.hasNext()){
            source = iterator.next();
            neighbour = source.getRightNeighbour();
            if (source.getCoordinate().distance(neighbour.getCoordinate()) < (double) info.getAgentDistance()/3){
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
