package ru.nachos.core.replanning.events;

import ru.nachos.core.replanning.lib.Event;
import ru.nachos.core.utils.AgentMap;

public class AgentsTooCloseMovedEvent extends Event {

    private int minDistance;
    private AgentMap agents;

    public AgentsTooCloseMovedEvent(int iterNum, int minDistance, AgentMap agents) {
        super(iterNum);
        this.minDistance = minDistance;
        this.agents = agents;
    }
    @Override
    public String getEventType() {
        return "move_close";
    }

    public int getMinDistance() { return minDistance; }

    public AgentMap getAgents() { return agents; }
}
