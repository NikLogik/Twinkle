package ru.nachos.core.replanning.events;

import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.replanning.Event;

public class AgentInsideFirefrontEvent extends Event {

    private Controller controller;

    public AgentInsideFirefrontEvent(int iterNum, Controller controller) {
        super(iterNum);
        this.controller = controller;
    }

    @Override
    public String getEventType() { return "inside_firefront_event"; }

    public Controller getController() { return controller; }
}

