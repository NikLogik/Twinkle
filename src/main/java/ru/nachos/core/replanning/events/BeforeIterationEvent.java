package ru.nachos.core.replanning.events;

import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.replanning.lib.Event;

public class BeforeIterationEvent extends Event {

    private IterationInfo info;

    public BeforeIterationEvent(IterationInfo info) {
        super(info.getIterNum());
        this.info = info;
    }

    @Override
    public String getEventType() {
        return "iteration_start_event";
    }

    public IterationInfo getInfo() { return info; }
}
