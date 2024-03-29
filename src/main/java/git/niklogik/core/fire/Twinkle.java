package git.niklogik.core.fire;

import org.locationtech.jts.geom.Coordinate;
import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentState;
import git.niklogik.core.fire.lib.AgentStatus;
import git.niklogik.core.network.lib.PolygonV2;

import java.util.Map;
import java.util.TreeMap;

class Twinkle implements Agent {

    private final Id<Agent> id;
    private Coordinate coord;
    private double speed;
    private double direction;
    private Agent leftNeighbour;
    private Agent rightNeighbour;
    private double distanceFromStart;
    private Id<PolygonV2> polygonId;
    private AgentStatus status;

    private boolean isStopped = false;

    private TreeMap<Integer, AgentState> planList = new TreeMap<>();
    private boolean head = false;
    Twinkle (Id<Agent> id){
        this.id = id;
    }

    @Override
    public Id<Agent> getId() { return this.id; }

    @Override
    public AgentState saveState(int iterNum){
        return planList.put(iterNum, new TwinkleState(this, iterNum));
    }

    @Override
    public boolean removeState(AgentState state) {
        return this.planList.remove(state.getIterNum(), state);
    }

    @Override
    public void setDirection(double direction) {
        this.direction = direction;
    }

    @Override
    public AgentState getStateByIter(int iterNum){
        return this.planList.get(iterNum);
    }

    @Override
    public void setRightNeighbour(Agent twinkle){ this.rightNeighbour = twinkle; }

    @Override
    public void setLeftNeighbour(Agent twinkle) {this.leftNeighbour = twinkle; }
    public void setSpeed(double speed){this.speed = speed;}

    @Override
    public Map<Integer, AgentState> getStates() { return this.planList; }
    @Override
    public AgentState getLastState() { return planList.lastEntry().getValue(); }

    @Override
    public Agent getLeftNeighbour() { return leftNeighbour; }

    @Override
    public Agent getRightNeighbour() { return rightNeighbour; }

    @Override
    public Coordinate getCoordinate() { return this.coord; }

    @Override
    public double getSpeed() { return this.speed; }

    @Override
    public Id<PolygonV2> getPolygonId() { return polygonId; }

    @Override
    public void setPolygonId(Id<PolygonV2> polygonId) { this.polygonId = polygonId; }

    @Override
    public double getDirection() { return this.direction; }

    @Override
    public void setCoordinate(Coordinate coord) { this.coord = coord; }

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
    @Override
    public boolean isStopped() { return isStopped; }
    @Override
    public void setStopped(boolean stopped) { isStopped = stopped; }

    @Override
    public AgentStatus getStatus() {
        return status;
    }
    @Override
    public void setStatus(AgentStatus status) {
        this.status = status;
    }

    public static class TwinkleState implements AgentState {

        private final Id<Agent> agent;
        private final double distanceFromStart;
        private final Coordinate coord;
        private final double speed;
        private final Agent leftNeighbour;
        private final Agent rightNeighbour;
        private final boolean head;
        private final int iterNum;
        private final AgentStatus status;

        private final Id<PolygonV2> polygonId;

        TwinkleState(Agent agent, int iterNum){
            this.iterNum = iterNum;
            this.agent = agent.getId();
            this.distanceFromStart = agent.getDistanceFromStart();
            this.coord = agent.getCoordinate();
            this.speed = agent.getSpeed();
            this.leftNeighbour = agent.getLeftNeighbour();
            this.rightNeighbour = agent.getRightNeighbour();
            this.head = agent.isHead();
            this.polygonId = agent.getPolygonId();
            this.status = agent.getStatus();
        }

        @Override
        public Id<Agent> getAgent() {
            return this.agent;
        }
        @Override
        public Id<PolygonV2> getPolygonId() { return polygonId; }
        @Override
        public double getDistanceFromStart() { return this.distanceFromStart; }
        @Override
        public Coordinate getCoordinate() {
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
        @Override
        public AgentStatus getStatus() { return this.status; }
    }
}
