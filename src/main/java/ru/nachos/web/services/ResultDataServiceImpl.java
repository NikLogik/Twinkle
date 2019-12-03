package ru.nachos.web.services;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.web.models.AgentIterDataImpl;
import ru.nachos.web.models.ResultDataImpl;
import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.lib.ResultDataService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ResultDataServiceImpl implements ResultDataService {

    @Override
    public ResultData prepareResult(Map<Id<Agent>, Agent> agents, InitialPreprocessingData data) {
        MathTransform transformation = data.getReTransformation();
        List<AgentIterData> statesList = agents.values().stream().map(Agent::getStates)
                                            .map(Map::values).flatMap(Collection::stream)
                                            .map(state-> new AgentIterDataImpl(state.getIterNum(), state.getAgent().toString(), state.getCoord()))
                                            .collect(Collectors.toList());
        TreeMap<Integer, List<AgentIterData>> resultMap = new TreeMap<>(statesList.stream().collect(Collectors.groupingBy(AgentIterData::getIterNum)));
        for (List<AgentIterData> list : resultMap.values()){
            for (AgentIterData agentIterData : list){
                try {
                    agentIterData.setCoordinates(JTS.transform(agentIterData.getCordinates(), null, transformation));
                } catch (TransformException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResultDataImpl(resultMap);
    }

    private String joinCoordinates(Coordinate coordinate){
        return coordinate.x + "," + coordinate.y;
    }
}
