package ru.nachos.core.events;

public abstract class Event {

    private int iterNum;

    public Event(int iterNum) {
        this.iterNum = iterNum;
    }

    public int getIterNum(){ return this.iterNum; }

    public abstract String getEventType();

}
