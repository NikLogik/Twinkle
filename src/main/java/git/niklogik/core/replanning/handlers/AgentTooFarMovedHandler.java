package git.niklogik.core.replanning.handlers;

import git.niklogik.core.Id;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.TwinkleUtils;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentStatus;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.replanning.events.AgentsTooFarMovedEvent;
import git.niklogik.core.replanning.lib.Event;
import git.niklogik.core.replanning.lib.EventHandler;
import git.niklogik.core.utils.AgentMap;
import git.niklogik.core.utils.GeodeticCalculator;
import git.niklogik.core.utils.JtsTools;
import git.niklogik.core.utils.PolygonType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.network.NetworkUtils;

import java.util.*;

public class AgentTooFarMovedHandler implements EventHandler {

    Logger logger = LoggerFactory.getLogger(AgentTooFarMovedHandler.class);

    private final String POSTFIX = ":twinkle";
    private IterationInfo info;
    private final Map<Id<Agent>, Agent> cacheActive = new LinkedHashMap<>();
    private final Set<Id<Agent>> cacheStopped = new TreeSet<>();
    private Polygon firePolygon;
    private double multipliedDistance;

    @Override
    public void handleEvent(Event event) {
        if (event instanceof AgentsTooFarMovedEvent tooFarMovedEvent){
            handleEvent(tooFarMovedEvent);
        }
    }

    public void handleEvent(AgentsTooFarMovedEvent event) {
        logger.info("Start to find agents with too far distance");
        this.info = event.getInfo();
        this.firePolygon = FireUtils.getPolygonFromAgentMap(info.getAgents(), new GeometryFactory());
        multipliedDistance = info.getAgentDistance() * 1.5;
//        while (checkDistance(info.getAgents())) {
            Iterator<Agent> iterator = info.getAgents().iterator();
            while (iterator.hasNext()) {
                Agent agent = iterator.next();
                if (agent.getCoordinate().distance(agent.getRightNeighbour().getCoordinate()) > multipliedDistance) {
                    setMiddleNeighbour(agent, agent.getRightNeighbour(), event.getCounter() + "-" + event.getIterNum());
                }
            }
            cacheActive.forEach(info.getAgents()::put);
            if (!cacheStopped.isEmpty()) {
                cacheStopped.forEach(info.getAgents()::setToStopped);
            }
            cacheActive.clear();
            cacheStopped.clear();
//        }
    }

    private void setMiddleNeighbour(Agent left, Agent right, String numberId){
        Coordinate position = GeodeticCalculator.middleCoordinate(left.getCoordinate(), right.getCoordinate());
        PolygonV2 geometry = NetworkUtils.findPolygonByAgentCoords(new GeometryFactory(), info.getPolygons(), position);
        Agent newAgent = null;
        if (PolygonType.isFireproof(geometry.getPolygonType())) {
            GeometryCollection geometryCollection = JtsTools.splitPolygon(geometry, left.getCoordinate(), right.getCoordinate());
            for (int i=0; i<geometryCollection.getNumGeometries(); i++){
                if (geometryCollection.getGeometryN(i).within(firePolygon)){
                    Geometry geometryN = geometryCollection.getGeometryN(i);
                    Agent r = right;
                    for (int j=1; j<geometryN.getCoordinates().length-1; j++) {
                        newAgent = info.getFireFactory().createTwinkle(Id.createAgentId(numberId + POSTFIX + "-" + j));
                        TwinkleUtils.setAgentBetween(left, r, newAgent);
                        newAgent.setPolygonId(r.getPolygonId());
                        newAgent.setCoordinate(geometryN.getCoordinates()[j]);
                        double direction = GeodeticCalculator.reverseProblem(newAgent.getCoordinate(), info.getFireCenter());
                        newAgent.setDirection(direction);
                        info.getCalculator().calculateSpeedOfSpreadWithArbitraryDirection(info.getFireSpeed(), newAgent, info.getHeadDirection());
                        newAgent.setStatus(AgentStatus.STOPPED);
                        newAgent.setDistanceFromStart(info.getFireCenter().distance(newAgent.getCoordinate()));
                        cacheActive.put(newAgent.getId(), newAgent);
                        cacheStopped.add(newAgent.getId());
                        r = newAgent;
                    }
                }
            }
        } else {
            newAgent = info.getFireFactory().createTwinkle(Id.createAgentId(numberId + POSTFIX));
            TwinkleUtils.createMiddleAgent(left, right, newAgent);
            newAgent.setPolygonId(geometry.getId());
            if (left.getStatus().equals(AgentStatus.STOPPED) && right.getStatus().equals(AgentStatus.STOPPED)){
                newAgent.setStatus(AgentStatus.STOPPED);
                cacheStopped.add(newAgent.getId());
            } else if (left.getStatus().equals(AgentStatus.STOPPED) && right.getStatus().equals(AgentStatus.ACTIVE)
                        || left.getStatus().equals(AgentStatus.ACTIVE) && right.getStatus().equals(AgentStatus.STOPPED)){
                double v = GeodeticCalculator.ortoDirection(left.getCoordinate(), right.getCoordinate(), GeodeticCalculator.middleCoordinate(left.getCoordinate(), right.getCoordinate()));
                newAgent.setDirection(v);
                info.getCalculator().calculateSpeedOfSpreadWithArbitraryDirection(info.getFireSpeed(), newAgent, info.getHeadDirection());
                newAgent.setStatus(AgentStatus.ACTIVE);
            } else {
                newAgent.setStatus(AgentStatus.ACTIVE);
            }
            cacheActive.put(newAgent.getId(), newAgent);
        }
        if (newAgent != null){
            NetworkUtils.createTrip(newAgent, info.getNetwork(), info.getSimTime(), info.getCurTime(), info.getCalculator());
        }
    }

    @Override
    public void resetHandler() {
        this.info = null;
        cacheActive.clear();
    }

    private boolean checkDistance(AgentMap map){
        Iterator<Agent> iterator = map.iterator();
        while (iterator.hasNext()){
            Agent next = iterator.next();
            if (next.getCoordinate().distance(next.getRightNeighbour().getCoordinate()) > multipliedDistance){
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
