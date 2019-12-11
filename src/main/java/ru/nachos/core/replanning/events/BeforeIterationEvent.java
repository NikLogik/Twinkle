package ru.nachos.core.replanning.events;

import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.replanning.Event;

public class BeforeIterationEvent extends Event {

    private Controller controller;

    public BeforeIterationEvent(Controller controller, int iterNum) {
        super(iterNum);
        this.controller = controller;
    }

    @Override
    public String getEventType() {
        return "iteration_start_event";
    }

    public Controller getController() { return controller; }
}
