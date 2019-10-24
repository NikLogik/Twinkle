package ru.nachos.core.fire;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.geo.Polygon;
import ru.nachos.core.Coord;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentPlan;

class TwinklePlan implements AgentPlan, Comparable<TwinklePlan> {

    private Agent twinkle;
    private int distanceFromStart;
    private Coord coord;
    private Id<Polygon> idPolygon;
    private double speed;
    private long timeStamp;
    private int itersNumber;

    public Id<Polygon> getIdPolygon() { return idPolygon; }

    public void setIdPolygon(Id<Polygon> idPolygon) { this.idPolygon = idPolygon; }

    public double getSpeed() { return speed; }

    public void setSpeed(double speed) { this.speed = speed; }

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
