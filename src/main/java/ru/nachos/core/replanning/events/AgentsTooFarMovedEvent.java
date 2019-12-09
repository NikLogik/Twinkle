package ru.nachos.core.replanning.events;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.replanning.Event;
import ru.nachos.core.fire.lib.Agent;

import java.util.Map;

public class AgentsTooFarMovedEvent extends Event {

    private final int fireAgentsDistance;
    private final Coordinate center;
    private Map<Id<Agent>, Agent> agents;
    private Fire fire;

    public AgentsTooFarMovedEvent(int iterNum, int fireAgentsDistance, Fire fire) {
        super(iterNum);
        this.fireAgentsDistance = fireAgentsDistance;
        this.center = fire.getCenterPoint();
        this.agents = fire.getTwinkles();
        this.fire = fire;
    }

    public Map<Id<Agent>, Agent> getAgents() { return agents; }

    public int getFireAgentsDistance() { return fireAgentsDistance; }

    public Coordinate getCenter() { return this.center; }

    public Fire getFire() { return this.fire; }

    @Override
    public String getEventType() {
        return "moved_away";
    }
}