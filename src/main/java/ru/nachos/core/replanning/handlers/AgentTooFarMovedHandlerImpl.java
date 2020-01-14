package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.Polygon;
import org.apache.log4j.Logger;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.TwinkleUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentStatus;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.events.AgentsTooFarMovedEvent;
import ru.nachos.core.replanning.handlers.lib.AgentTooFarMovedHandler;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.core.utils.JtsTools;
import ru.nachos.core.utils.PolygonType;

import java.util.*;

public class AgentTooFarMovedHandlerImpl implements AgentTooFarMovedHandler {

    Logger logger = Logger.getLogger(AgentTooFarMovedHandlerImpl.class);

    private final String POSTFIX = ":twinkle";
    private IterationInfo info;
    private Map<Id<Agent>, Agent> cacheActive = new LinkedHashMap<>();
    private Set<Id<Agent>> cacheStopped = new TreeSet<>();
    private Polygon firePolygon;
    private double multipliedDistance;

    @Override
    public void handleEvent(AgentsTooFarMovedEvent event) {
        logger.info("Start to find agents with too far distance");
        this.info = event.getInfo();
        this.firePolygon = FireUtils.getPolygonFromAgentMap(info.getAgents(), info.getGeomFactory());
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
        PolygonV2 geometry = NetworkUtils.findPolygonByAgentCoords(info.getGeomFactory(), info.getPolygons(), position);
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
