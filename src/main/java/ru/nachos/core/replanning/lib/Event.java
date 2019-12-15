package ru.nachos.core.replanning.lib;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Event {

    private AtomicLong counter = new AtomicLong(1000);
    private int iterNum;

    public Event(int iterNum) {
        this.iterNum = iterNum;
    }

    public int getIterNum(){ return this.iterNum; }

    public abstract String getEventType();

    public synchronized long getCounter() {
        return counter.incrementAndGet();
    }
}
