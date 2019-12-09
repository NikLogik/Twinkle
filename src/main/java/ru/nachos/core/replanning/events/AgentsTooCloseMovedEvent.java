package ru.nachos.core.replanning.events;

import ru.nachos.core.Id;
import ru.nachos.core.replanning.Event;
import ru.nachos.core.fire.lib.Agent;

import java.util.Map;

public class AgentsTooCloseMovedEvent extends Event {

    private Agent source;
    private Map<Id<Agent>, Agent> agents;

    public AgentsTooCloseMovedEvent(int iterNum, Agent agent, Map<Id<Agent>, Agent> agents) {
        super(iterNum);
        this.source = agent;
        this.agents = agents;
    }

    @Override
    public String getEventType() {
        return "move_close";
    }

    public Agent getSource() { return source; }

    public Map<Id<Agent>, Agent> getAgents() { return agents; }
}
