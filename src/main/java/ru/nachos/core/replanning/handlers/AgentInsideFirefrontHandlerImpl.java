package ru.nachos.core.replanning.handlers;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import org.apache.log4j.Logger;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.events.AgentInsideFirefrontEvent;
import ru.nachos.core.replanning.handlers.lib.AgentInsideFirefrontHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AgentInsideFirefrontHandlerImpl implements AgentInsideFirefrontHandler {

    Logger logger = Logger.getLogger(AgentInsideFirefrontHandler.class);

    private GeometryFactory factory = new GeometryFactory();
    private Set<Id<Agent>> removeAgents = new HashSet<>();

    @Override
    public void handleEvent(AgentInsideFirefrontEvent event) {
        Map<Id<Agent>, Agent> agentsForIter = event.getController().getAgentsForIter(event.getIterNum());
        List<Coordinate> collect = agentsForIter.values().stream().map(Agent::getCoordinate).collect(Collectors.toList());
        collect.add(collect.get(0));
        Coordinate[] coordinates = collect.toArray(new Coordinate[0]);
        Polygon polygon = factory.createPolygon(coordinates);
        for (Agent agent : agentsForIter.values()){
            if (polygon.contains(factory.createPoint(agent.getCoordinate()))){
                removeAgents.add(agent.getId());
            }
        }
        removeAgents.forEach(event.getController().getAgentsForIter(event.getIterNum())::remove);

        logger.info("Remove " + removeAgents.size() + " agents within firefront");
    }

    @Override
    public void resetHandler() {
        removeAgents.clear();
    }

    @Override
    public void persistEvents() {

    }
}
