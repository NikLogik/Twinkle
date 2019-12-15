package ru.nachos.core.replanning.events;

import ru.nachos.core.replanning.lib.Event;

public class CrossedFiresEvent extends Event {

    public CrossedFiresEvent(int iterNum) {
        super(iterNum);
    }

    @Override
    public String getEventType() {
        return "crossed_fire";
    }
}
