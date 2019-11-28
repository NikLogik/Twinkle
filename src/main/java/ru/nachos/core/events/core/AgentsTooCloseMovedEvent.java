package ru.nachos.core.events.core;

import ru.nachos.core.Id;
import ru.nachos.core.events.Event;
import ru.nachos.core.fire.lib.Agent;

public class AgentsTooCloseMovedEvent extends Event {

    private Id<Agent> left;
    private Id<Agent> right;

    public AgentsTooCloseMovedEvent(int iterNum) {
        super(iterNum);
    }

    @Override
    public String getEventType() {
        return "move_close";
    }

    public Id<Agent> getLeft() {
        return left;
    }

    public Id<Agent> getRight() {
        return right;
    }
}
