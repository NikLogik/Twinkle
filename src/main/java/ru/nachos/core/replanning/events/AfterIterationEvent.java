package ru.nachos.core.replanning.events;

import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.replanning.lib.Event;

public class AfterIterationEvent extends Event {

    private IterationInfo info;

    public AfterIterationEvent(int iterNum, IterationInfo info) {
        super(iterNum);
        this.info = info;
    }

    public IterationInfo getInfo() { return info; }

    @Override
    public String getEventType() {
        return "iteration_end_event";
    }
}
