package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.events.AgentInsideFirefrontEvent;
import ru.nachos.core.replanning.handlers.lib.AgentInsideFirefrontHandler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class AgentInsideFirefrontHandlerImpl implements AgentInsideFirefrontHandler {

//    Logger logger = Logger.getLogger(AgentInsideFirefrontHandler.class);

    private GeometryFactory factory = new GeometryFactory();
    private Set<Id<Agent>> removeCandidates = new HashSet<>();

    @Override
    public void handleEvent(AgentInsideFirefrontEvent event) {
        LinkedList<Coordinate> coordinates = new LinkedList<>();
        Iterator<Agent> iterator = event.getAgents().iterator();
        Coordinate coordinate;
        while (iterator.hasNext()){
            coordinate = iterator.next().getCoordinate();
            coordinates.add(coordinate);
        }
        coordinates.add(coordinates.getFirst());
        Coordinate[] var = coordinates.toArray(new Coordinate[0]);
        Polygon polygon = factory.createPolygon(var);
        for (Agent agent : event.getAgents().values()){
            if (polygon.contains(factory.createPoint(agent.getCoordinate()))){
                removeCandidates.add(agent.getId());
            }
        }
        for (Id<Agent> id : removeCandidates){
            event.getAgents().remove(id);
        }
//        logger.info("Remove " + removeCandidates.size() + " agents within firefront");
    }

    @Override
    public void resetHandler() {
        removeCandidates.clear();
    }

    @Override
    public void persistEvents() {

    }
}
