package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import org.jetbrains.annotations.NotNull;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.network.lib.PolygonV2;

class TwinkleState implements AgentState, Comparable<TwinkleState> {

    private Agent twinkle;
    private int distanceFromStart;
    private Coordinate coord;
    private Id<PolygonV2> idPolygon;
    private double speed;
    private long timeStamp;
    private int itersNumber;

    TwinkleState(){}

    TwinkleState(Agent agent){
        this.twinkle = agent;
        this.coord = agent.getCoordinate();
        this.speed = agent.getSpeed();
    }

    @Override
    public Id<PolygonV2> getIdPolygon() { return idPolygon; }

    public void setIdPolygon(Id<PolygonV2> idPolygon) { this.idPolygon = idPolygon; }

    public double getSpeed() { return speed; }

    @Override
    public Agent getAgent() {
        return this.twinkle;
    }

    @Override
    public void setAgent(Agent twinkle) { this.twinkle = twinkle; }

    @Override
    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    @Override
    public void setDistanceFromStart(int distance) {
        this.distanceFromStart = distance;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coord;
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
    public int compareTo(@NotNull TwinkleState o) {
        return this.itersNumber - o.getItersNumber();
    }
}
