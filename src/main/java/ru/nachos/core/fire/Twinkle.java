package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Point;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;

import java.util.ArrayList;
import java.util.List;

class Twinkle implements Agent {

    private Id<Agent> id;
    private Point coord;
    private double speed;
    private double direction;
    private Agent leftNeighbour;
    private Agent rightNeighbour;
    private List<AgentState> planList = new ArrayList<>(5);
    private boolean head = false;

    Twinkle (Id<Agent> id){
        this.id = id;
    }

    public boolean addState(AgentState plan){
        plan.setAgent(this);
        return this.planList.add(plan);
    }

    public boolean removeState(AgentState plan) {
        return this.planList.remove(plan);
    }

    @Override
    public void setRightNeighbour(Agent twinkle){ this.rightNeighbour = twinkle; }
    @Override
    public void setLeftNeighbour(Agent twinkle) {this.leftNeighbour = twinkle; }
    public void setSpeed(double speed){this.speed = speed;}

    @Override
    public Id<Agent> getId() { return this.id; }

    @Override
    public List<AgentState> getPlans() { return this.planList; }

    @Override
    public AgentState getLastState() {
        return planList.get(planList.size()-1);
    }

    @Override
    public Agent getLeftNeighbour() { return leftNeighbour; }

    @Override
    public Agent getRightNeighbour() { return rightNeighbour; }

    @Override
    public Point getPoint() { return this.coord; }

    @Override
    public double getSpeed() { return this.speed; }

    @Override
    public double getDirection() { return this.direction; }

    void setPoint(Point coord) { this.coord = coord; }

    @Override
    public boolean isHead() { return head; }

    @Override
    public void setHead(boolean head){ this.head = head; }
}
