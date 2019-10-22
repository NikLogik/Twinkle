package ru.nachos.core.fire;

import org.jetbrains.annotations.NotNull;
import ru.nachos.core.Coord;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentPlan;

class TwinklePlan implements AgentPlan, Comparable<TwinklePlan> {

    private Agent twinkle;
    private int distanceFromStart;
    private Coord coord;
    private long timeStamp;
    private int itersNumber;

    @Override
    public Agent getAgent() {
        return this.twinkle;
    }

    @Override
    public void setAgent(Agent agent) {
        this.twinkle = agent;
    }

    @Override
    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    @Override
    public void setDistanceFromStart(int distance) {
        this.distanceFromStart = distance;
    }

    @Override
    public Coord getCoord() {
        return this.coord;
    }

    @Override
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }

    @Override
    public void setItersNumber(int itersNumber) {
        this.itersNumber = itersNumber;
    }

    @Override
    public int getItersNumber() {
        return itersNumber;
    }


    @Override
    public int compareTo(@NotNull TwinklePlan o) {
        return this.itersNumber - o.getItersNumber();
    }
}
