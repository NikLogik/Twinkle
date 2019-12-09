package ru.nachos.core.replanning.events;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.replanning.Event;
import ru.nachos.core.fire.lib.Agent;

public class AgentsStoppedBarrierEvent extends Event {

    private Id<Agent> id;
    private Coordinate coordinate;

    public AgentsStoppedBarrierEvent(int iterNum, Id<Agent> id, Coordinate coordinate) {
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
