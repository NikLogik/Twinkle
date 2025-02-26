package git.niklogik.core.fire;


import git.niklogik.core.config.lib.Config;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentState;
import git.niklogik.core.fire.lib.AgentStatus;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.utils.AgentMap;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public final class FireUtils {

    private FireUtils() {}

    public static Fire createFire() {
        return new FireImpl();
    }

    public static Fire createFire(Config config) {
        if (config != null) {
            return new FireImpl(config);
        } else {
            return createFire();
        }
    }

    public static void setHeadDirectionOfSpread(Fire fire, double directionOfSpread) {
        ((FireImpl) fire).setHeadDirection(directionOfSpread);
    }

    /**
     * This method add new agent in the fire front near by target agent.
     *
     * @param twinkles    - list of fire agents
     * @param target      - target agent
     * @param newTwinkle- new agent which will be add
     * @param side        - if true, agent will be added on the right side from target agent, otherwise on the left side
     */
    public static void setNewTwinkleToFireFront(Map<UUID, Agent> twinkles, Twinkle target, Twinkle newTwinkle, boolean side) {
        if (side) {
            target.setRightNeighbour(newTwinkle);
        } else {
            target.setLeftNeighbour(newTwinkle);
        }
        twinkles.put(newTwinkle.getId(), newTwinkle);
    }

    public static Agent getHeadAgent(Map<UUID, Agent> agents) {
        List<Agent> leaders = agents.values().stream().filter(Agent::isHead).toList();

        if (leaders.size() > 1) {
            log.debug("Fire contains more than one head agent.");
            var newLeader = leaders.getFirst();
            newLeader.setHead(true);

            leaders.stream()
                   .filter(agent -> agent.getId().equals(newLeader.getId()))
                   .forEach(agent -> agent.setHead(false));

            return newLeader;
        } else if (leaders.isEmpty()) {
            log.debug("No head agent found.");
            var newLeader = agents.values().iterator().next();
            newLeader.setHead(true);
            return newLeader;
        }

        return leaders.getFirst();
    }

    public static Polygon getPolygonFromAgentMap(AgentMap map, GeometryFactory factory) {
        Iterator<Agent> iterator = map.iterator();
        LinkedList<Coordinate> coordinates = new LinkedList<>();

        do {
            Agent next = iterator.next();
            coordinates.add(new Coordinate(next.getCoordinate()));
        } while (iterator.hasNext());

        return factory.createPolygon(coordinates.toArray(Coordinate[]::new));
    }

    public static LinkedList<AgentState> getListOfStates(AgentMap agents, int iterNum) {
        LinkedList<AgentState> states = new LinkedList<>();
        for (Agent next : agents) {
            if (next.getPlanList().containsKey(iterNum) && !next.getStatus().equals(AgentStatus.DISABLED)) {
                states.add(next.getStateByIter(iterNum));
            }
        }
        return states;
    }
}
