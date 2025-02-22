package git.niklogik.core.fire;

import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentState;
import git.niklogik.core.fire.lib.AgentStatus;
import git.niklogik.core.network.lib.PolygonV2;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;
import java.util.TreeMap;
import java.util.UUID;

import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Twinkle implements Agent {

    private final UUID id;
    private Coordinate coordinate;
    private BigDecimal speed;
    private BigDecimal direction;
    private Agent leftNeighbour;
    private Agent rightNeighbour;
    private BigDecimal distanceFromStart;
    private Id<PolygonV2> polygonId;
    private AgentStatus status;

    private boolean isStopped = false;

    private final TreeMap<Integer, AgentState> planList = new TreeMap<>();

    private boolean head = false;

    @Override
    public AgentState saveState(int iterNum) {
        return planList.put(iterNum, new TwinkleState(this, iterNum));
    }

    @Override
    public boolean removeState(AgentState state) {
        return this.planList.remove(state.getIterNum(), state);
    }

    @Override
    public AgentState getStateByIter(int iterNum) {
        return this.planList.get(iterNum);
    }

    @Override
    public AgentState getLastState() {return planList.lastEntry().getValue();}

    @Override
    public boolean isHead() {return head;}

    @Override
    public void setHead(boolean head) {this.head = head;}

    @Override
    public void setDistanceFromStart(double distanceFromStart) {
        this.distanceFromStart = toBigDecimal(distanceFromStart);
    }

    @Override
    public boolean isStopped() {return isStopped;}

    @Getter
    public static class TwinkleState implements AgentState {

        private final UUID agent;
        private final BigDecimal distanceFromStart;
        private final Coordinate coordinate;
        private final BigDecimal speed;
        private final Agent leftNeighbour;
        private final Agent rightNeighbour;
        private final boolean head;
        private final int iterNum;
        private final AgentStatus status;

        private final Id<PolygonV2> polygonId;

        TwinkleState(Agent agent, int iterNum) {
            this.iterNum = iterNum;
            this.agent = agent.getId();
            this.distanceFromStart = agent.getDistanceFromStart();
            this.coordinate = agent.getCoordinate();
            this.speed = agent.getSpeed();
            this.leftNeighbour = agent.getLeftNeighbour();
            this.rightNeighbour = agent.getRightNeighbour();
            this.head = agent.isHead();
            this.polygonId = agent.getPolygonId();
            this.status = agent.getStatus();
        }
    }
}
