package ru.nachos.core.replanning.handlers;

import ru.nachos.core.Id;
import ru.nachos.core.fire.TwinkleUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.events.AgentsTooCloseMovedEvent;
import ru.nachos.core.replanning.handlers.lib.AgentTooCloseMovedHandler;

import java.util.ArrayList;
import java.util.List;

public class AgentTooCloseMovedHandlerImpl implements AgentTooCloseMovedHandler {

    List<Id<Agent>> agents = new ArrayList<>();

    @Override
    public void handleEvent(AgentsTooCloseMovedEvent event) {
        agents.add(event.getSource().getRightNeighbour().getId());
        Agent rightNeighbour = event.getSource().getRightNeighbour();
        TwinkleUtils.removeRightNeighbour(rightNeighbour, event.getSource());
        rightNeighbour.setSpeed(0.0);
    }

    @Override
    public void resetHandler() { agents.clear(); }

    @Override
    public void persistEvents() {

    }
}
