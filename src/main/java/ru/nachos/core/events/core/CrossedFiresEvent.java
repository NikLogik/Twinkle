package ru.nachos.core.events.core;

import ru.nachos.core.events.Event;

public class CrossedFiresEvent extends Event {

    public CrossedFiresEvent(int iterNum) {
        super(iterNum);
    }

    @Override
    public String getEventType() {
        return "crossed_fire";
    }
}
