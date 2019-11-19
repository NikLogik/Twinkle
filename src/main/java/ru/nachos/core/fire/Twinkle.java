package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentStateV2;

import java.util.Map;
import java.util.TreeMap;

class Twinkle implements Agent {

    private Id<Agent> id;
    private Coordinate coord;
    private double speed;
    private double direction;
    private Agent leftNeighbour;
    private Agent rightNeighbour;
    private double distanceFromStart;
    private TreeMap<Integer, AgentStateV2> planList = new TreeMap<>();
    private boolean head = false;

    Twinkle (Id<Agent> id){
        this.id = id;
    }

    @Override
    public Id<Agent> getId() { return this.id; }

    @Override
    public AgentStateV2 saveState(int iterNum){
        return planList.put(iterNum, new TwinkleStateV2(this, iterNum));
    }

    @Override
    public boolean removeState(AgentStateV2 state) {
        return this.planList.remove(state.getIterNum(), state);
    }

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
    public Map<Integer, AgentStateV2> getStates() { return this.planList; }

    @Override
    public AgentStateV2 getLastState() {
        return planList.lastEntry().getValue();
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

    @Override
    public void setDistanceFromStart(double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }
    @Override
    public double getDistanceFromStart() {
        return distanceFromStart;
    }

    public class TwinkleStateV2 implements AgentStateV2 {
        private Id<Agent> agent;
        private double distanceFromStart;
        private Coordinate coord;
        private double speed;
        private Agent leftNeighbour;
        private Agent rightNeighbour;
        private boolean head;
        private int iterNum;

        TwinkleStateV2(Agent agent, int iterNum){
            this.iterNum = iterNum;
            this.agent = agent.getId();
            this.distanceFromStart = agent.getDistanceFromStart();
            this.coord = agent.getCoordinate();
            this.speed = agent.getSpeed();
            this.leftNeighbour = agent.getLeftNeighbour();
            this.rightNeighbour = agent.getRightNeighbour();
            this.head = agent.isHead();
        }
        @Override
        public Id<Agent> getAgent() {
            return this.agent;
        }
        @Override
        public double getDistanceFromStart() { return this.distanceFromStart; }
        @Override
        public Coordinate getCoord() {
            return this.coord;
        }
        @Override
        public double getSpeed() {
            return this.speed;
        }
        @Override
        public Agent getLeftNeighbour() {
            return this.leftNeighbour;
        }
        @Override
        public Agent getRightNeighbour() {
            return this.rightNeighbour;
        }
        @Override
        public boolean isHead() {
            return this.head;
        }
        @Override
        public int getIterNum() {
            return this.iterNum;
        }
    }
}
