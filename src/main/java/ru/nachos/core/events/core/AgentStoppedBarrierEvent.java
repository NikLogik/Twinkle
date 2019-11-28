package ru.nachos.core.events.core;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.events.Event;
import ru.nachos.core.fire.lib.Agent;

public class AgentStoppedBarrierEvent extends Event {

    private Id<Agent> id;
    private Coordinate coordinate;

    public AgentStoppedBarrierEvent(int iterNum, Id<Agent> id, Coordinate coordinate) {
        super(iterNum);
        this.id = id;
        this.coordinate = coordinate;
    }

    @Override
    public String getEventType() {
        return "stopped";
    }

    public Id<Agent> getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
