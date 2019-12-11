package ru.nachos.core.replanning.events;

import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.Event;

import java.util.Map;

public class AgentsTooCloseMovedEvent extends Event {

    private Agent source;

    private Controller controller;

    private int minDistance;
    private Map<Id<Agent>, Agent> agents;

    public AgentsTooCloseMovedEvent(int iterNum, int minDistance, Controller controller) {
        super(iterNum);
        this.minDistance = minDistance;
        this.controller = controller;
    }
    @Override
    public String getEventType() {
        return "move_close";
    }

    public int getMinDistance() { return minDistance; }

    public Controller getController() { return controller; }
}
