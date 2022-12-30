package git.niklogik.core.replanning.handlers;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import git.niklogik.core.Id;
import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentStatus;
import git.niklogik.core.replanning.events.SelfCrossingFireFrontEvent;
import git.niklogik.core.replanning.lib.Event;
import git.niklogik.core.replanning.lib.EventHandler;
import git.niklogik.core.utils.AgentMap;
import git.niklogik.db.repository.osm.OsmDatabaseManager;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SelfCrossingFireFrontHandler implements EventHandler {

    Logger logger = LoggerFactory.getLogger(SelfCrossingFireFrontHandler.class);

    private IterationInfo info;
    private final String POSTFIX = ":crossed";
    private Set<Id<Agent>> removeIds = new TreeSet<>();

    @Override
    public void handleEvent(Event event) {
        if (event instanceof SelfCrossingFireFrontEvent crossingFireFrontEvent){
            handleEvent(crossingFireFrontEvent);
        }
    }

    public void handleEvent(SelfCrossingFireFrontEvent event) {
        logger.info("Start find self crossing fire events");
        this.info = event.getInfo();
        Polygon polygon = FireUtils.getPolygonFromAgentMap(info.getAgents(), new GeometryFactory());
        while (!polygon.isSimple()){
            removeIntersectionsAgents(info.getAgents());
            removeIds.forEach(info.getAgents()::setToDisable);
            polygon = FireUtils.getPolygonFromAgentMap(info.getAgents(), new GeometryFactory());
            if (removeIds.isEmpty()){
                break;
            } else {
                removeIds.clear();
            }
        }
    }

    private void removeIntersectionsAgents(AgentMap map){
        Iterator<Agent> iterator = map.iterator();
        Agent left = iterator.next();
        while (iterator.hasNext()) {
            Agent current = iterator.next();
            if (!removeIds.contains(current.getId())) {
                Point crossingPoint = findCrossingPoint(left.getCoordinate(), current.getCoordinate(),
                        current.getRightNeighbour().getCoordinate(), current.getRightNeighbour().getRightNeighbour().getCoordinate());
                if (crossingPoint != null) {
                    removeIds.add(current.getRightNeighbour().getId());
                    Agent _current = map.get(current.getId());
                    setMiddleParams(_current, crossingPoint.getCoordinate());
                }
            }
            left = current;
        }
        logger.info("Disabled agents with IDs: " + removeIds.toString());
    }

    private void setMiddleParams(Agent _current, Coordinate position){
        Agent left = _current.getLeftNeighbour();
        Agent right = _current.getRightNeighbour();
        double var = right.getDirection() < left.getDirection() ? right.getDirection() + 360.00 : right.getDirection();
        double direction = ((var - left.getDirection()) / 2) + left.getDirection();
        double speed = (left.getSpeed() + right.getSpeed()) / 2;
        _current.setStatus(AgentStatus.ACTIVE);
        _current.setCoordinate(position);
        _current.setSpeed(speed);
        _current.setDirection(direction);
        _current.setDistanceFromStart(info.getFireCenter().distance(position));
    }


    private Point findCrossingPoint(Coordinate p1, Coordinate p2, Coordinate p3, Coordinate p4){
        CoordinateSequenceFactory factory = CoordinateArraySequenceFactory.instance();
        GeometryFactory geometryFactory = new GeometryFactory();
        LineString string1 = geometryFactory.createLineString(factory.create(new Coordinate[]{p1, p2}));
        LineString string2 = geometryFactory.createLineString(factory.create(new Coordinate[]{p3, p4}));
        Geometry geometry = string1.intersection(string2);
        if (geometry!= null){
            if (OsmDatabaseManager.Definitions.POINT.equals(geometry.getGeometryType())){
                return (Point) geometry;
            }
        }
        return null;
    }

    @Override
    public void resetHandler() {

    }

    @Override
    public void persistEvents() {

    }
}
