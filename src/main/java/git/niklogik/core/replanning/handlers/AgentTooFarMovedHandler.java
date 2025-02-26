package git.niklogik.core.replanning.handlers;

import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.TwinkleUtils;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.replanning.events.AgentsTooFarMovedEvent;
import git.niklogik.core.replanning.lib.Event;
import git.niklogik.core.replanning.lib.EventHandler;
import git.niklogik.core.utils.AgentMap;
import git.niklogik.core.utils.GeodeticCalculator;
import git.niklogik.core.utils.JtsTools;
import git.niklogik.core.utils.PolygonType;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static git.niklogik.core.fire.lib.AgentStatus.ACTIVE;
import static git.niklogik.core.fire.lib.AgentStatus.STOPPED;

@Slf4j
public class AgentTooFarMovedHandler implements EventHandler {

    private final String POSTFIX = ":twinkle";
    private IterationInfo info;
    private final Map<UUID, Agent> cacheActive = new LinkedHashMap<>();
    private final Set<UUID> cacheStopped = new TreeSet<>();
    private Polygon firePolygon;
    private double multipliedDistance;

    @Override
    public void handleEvent(Event event) {
        if (event instanceof AgentsTooFarMovedEvent tooFarMovedEvent) {
            handleEvent(tooFarMovedEvent);
        }
    }

    public void handleEvent(AgentsTooFarMovedEvent event) {
        log.info("Start to find agents with too far distance");
        this.info = event.getInfo();
        this.firePolygon = FireUtils.getPolygonFromAgentMap(info.getAgents(), new GeometryFactory());
        multipliedDistance = info.getAgentDistance() * 1.5;
        for (Agent agent : info.getAgents()) {
            if (agent.getCoordinate().distance(agent.getRightNeighbour().getCoordinate()) > multipliedDistance) {
                setMiddleNeighbour(agent, agent.getRightNeighbour());
            }
        }
        cacheActive.forEach(info.getAgents()::put);
        if (!cacheStopped.isEmpty()) {
            cacheStopped.forEach(info.getAgents()::stopAgent);
        }
        cacheActive.clear();
        cacheStopped.clear();
    }

    private void setMiddleNeighbour(Agent left, Agent right) {
        var middleCoordinate = GeodeticCalculator.middleCoordinate(left.getCoordinate(), right.getCoordinate());
        PolygonV2 geometry = NetworkUtils.findPolygonByAgentCoords(new GeometryFactory(), info.getPolygons(), middleCoordinate);
        Agent newAgent = null;
        if (PolygonType.isFireproof(geometry.getPolygonType())) {
            GeometryCollection geometryCollection = JtsTools.splitPolygon(geometry, left.getCoordinate(),
                                                                          right.getCoordinate());
            for (int i = 0; i < geometryCollection.getNumGeometries(); i++) {
                if (geometryCollection.getGeometryN(i).within(firePolygon)) {
                    Geometry geometryN = geometryCollection.getGeometryN(i);
                    Agent r = right;
                    for (int j = 1; j < geometryN.getCoordinates().length - 1; j++) {
                        newAgent = info.getFireFactory().createTwinkle(POSTFIX + "-" + j);
                        TwinkleUtils.setAgentBetween(left, r, newAgent);
                        newAgent.setPolygonId(r.getPolygonId());
                        newAgent.setCoordinate(geometryN.getCoordinates()[j]);
                        var direction = GeodeticCalculator.reverseProblem(newAgent.getCoordinate(),
                                                                          info.getFireCenter());
                        newAgent.setDirection(direction);
                        var agentSpeed = info.getCalculator()
                                             .calculateForDirection(info.getFireSpeed(),
                                                                    direction,
                                                                    info.getHeadDirection());
                        newAgent.setSpeed(agentSpeed);
                        newAgent.setStatus(STOPPED);
                        newAgent.setDistanceFromStart(info.getFireCenter().distance(newAgent.getCoordinate()));
                        cacheActive.put(newAgent.getId(), newAgent);
                        cacheStopped.add(newAgent.getId());
                        r = newAgent;
                    }
                }
            }
        } else {
            newAgent = info.getFireFactory().createTwinkle(POSTFIX);
            TwinkleUtils.createMiddleAgent(left, right, newAgent);
            newAgent.setPolygonId(geometry.getId());
            if (left.getStatus().equals(STOPPED) && right.getStatus().equals(STOPPED)) {
                newAgent.setStatus(STOPPED);
                cacheStopped.add(newAgent.getId());
            } else if ((isStopped(left) && isActive(right))
                       || isActive(left) && isStopped(right)) {

                var agentDirection = GeodeticCalculator.ortoDirection(left.getCoordinate(),
                                                                      right.getCoordinate(),
                                                                      middleCoordinate);
                newAgent.setDirection(agentDirection);
                var agentSpeed = info.getCalculator()
                                     .calculateForDirection(info.getFireSpeed(),
                                                            agentDirection,
                                                            info.getHeadDirection());
                newAgent.setSpeed(agentSpeed);
                newAgent.setStatus(ACTIVE);
            } else {
                newAgent.setStatus(ACTIVE);
            }
            cacheActive.put(newAgent.getId(), newAgent);
        }
        if (newAgent != null) {
            NetworkUtils.createTrip(newAgent, info.getNetwork(), info.getSimTime(), info.getCurTime(),
                                    info.getCalculator());
        }
    }

    private boolean isActive(Agent agent) {
        return agent.getStatus() == ACTIVE;
    }

    private boolean isStopped(Agent agent) {
        return agent.getStatus() == STOPPED;
    }

    @Override
    public void resetHandler() {
        this.info = null;
        cacheActive.clear();
    }

    private boolean checkDistance(AgentMap map) {
        Iterator<Agent> iterator = map.iterator();
        while (iterator.hasNext()) {
            Agent next = iterator.next();
            if (next.getCoordinate().distance(next.getRightNeighbour().getCoordinate()) > multipliedDistance) {
                System.out.println(next.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void persistEvents() {

    }
}
