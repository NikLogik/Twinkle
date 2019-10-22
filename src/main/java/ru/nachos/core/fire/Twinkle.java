package ru.nachos.core.fire;

import ru.nachos.core.Coord;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentPlan;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Twinkle implements Agent {

    private Id<Agent> id;
    private Coord coord;
    private double speed;
    private double direction;
    private List<AgentPlan> planList = new ArrayList<>(5);

    Twinkle (Id<Agent> id){
        this.id = id;
    }

    void setCoord(Coord coord){
        this.coord = coord;
    }

    public boolean addPlan(AgentPlan plan){
        plan.setAgent(this);
        return this.planList.add(plan);
    }

    @Override
    public Id<Agent> getId() {
        return this.id;
    }

    @Override
    public List<AgentPlan> getPlans() {
        return this.planList;
    }

    @Override
    public Coord getCoord() {
        return this.coord;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public double getDirection() {
        return this.direction;
    }
}
