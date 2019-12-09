package ru.nachos.core.replanning.events;

import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.replanning.Event;

public class AfterIterationEvent extends Event {

    private Controller controller;

    public AfterIterationEvent(Controller controller, int iterNum) {
        super(iterNum);
        this.controller = controller;
    }

    public Controller getController() { return this.controller; }

    @Override
    public String getEventType() {
        return "iteration_end_event";
    }
}
