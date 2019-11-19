package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;

import java.util.Map;
import java.util.TreeMap;

class Twinkle implements Agent {

    private Id<Agent> id;
    private Coordinate coord;
    private double speed;
    private double direction;
    private Agent leftNeighbour;
    private Agent rightNeighbour;
    private TreeMap<Integer, AgentState> planList = new TreeMap<>();
    private boolean head = false;

    Twinkle (Id<Agent> id){
        this.id = id;
    }

    public AgentState addState(AgentState plan){
        plan.setAgent(this);
        return this.planList.put(plan.getItersNumber(), plan);
    }

    public boolean removeState(AgentState plan) { return this.planList.remove(plan.getItersNumber(), plan); }

    @Override
    public void setDirection(double direction) {
        this.direction = direction;
    }

    @Override
    public void setRightNeighbour(Agent twinkle){ this.rightNeighbour = twinkle; }
    @Override
    public void setLeftNeighbour(Agent twinkle) {this.leftNeighbour = twinkle; }
    public void setSpeed(double speed){this.speed = speed;}

    @Override
    public Id<Agent> getId() { return this.id; }

    @Override
    public Map<Integer, AgentState> getStates() { return this.planList; }

    @Override
    public AgentState getLastState() {
        int last = planList.keySet().stream().max(Integer::compare).get();
        return planList.get(last);
    }

    @Override
    public Agent getLeftNeighbour() { return leftNeighbour; }

    @Override
    public Agent getRightNeighbour() { return rightNeighbour; }

    @Override
    public Coordinate getCoordinate() { return this.coord; }

    @Override
    public double getSpeed() { return this.speed; }

    @Override
    public double getDirection() { return this.direction; }

    @Override
    public void setPoint(Coordinate coord) { this.coord = coord; }

    @Override
    public boolean isHead() { return head; }

    @Override
    public void setHead(boolean head){ this.head = head; }
}
