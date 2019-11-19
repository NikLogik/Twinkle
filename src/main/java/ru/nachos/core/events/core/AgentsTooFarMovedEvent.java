package ru.nachos.core.events.core;

import ru.nachos.core.Id;
import ru.nachos.core.events.Event;
import ru.nachos.core.fire.lib.Agent;

public class AgentsTooFarMovedEvent extends Event {

    private Id<Agent> left;
    private Id<Agent> right;

    public AgentsTooFarMovedEvent(int iterNum, Id<Agent> left, Id<Agent> right) {
        super(iterNum);
        this.left = left;
        this.right = right;
    }

    public Id<Agent> getLeft() {
        return this.left;
    }

    public Id<Agent> getRight() {
        return this.right;
    }

    @Override
    public String getEventType() {
        return "moved_away";
    }
}
