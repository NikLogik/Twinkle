package ru.nachos.web.services;

import com.vividsolutions.jts.geom.Coordinate;
import org.springframework.stereotype.Service;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.web.models.AgentIterDataImpl;
import ru.nachos.web.models.ResultDataImpl;
import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.ResultData;
import ru.nachos.web.services.lib.ResultDataService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultDataServiceImpl implements ResultDataService {

    @Override
    public ResultData prepareResult(Map<Id<Agent>, Agent> agents, Config config) {
        List<AgentIterData> statesList = agents.values().stream().map(Agent::getStates)
                                            .map(Map::values).flatMap(Collection::stream)
                                            .map(state-> new AgentIterDataImpl(state.getIterNum(), state.getAgent().toString(), joinCoordinates(state.getCoord())))
                                            .collect(Collectors.toList());
        Map<Integer, List<AgentIterData>> resultMap = statesList.stream().collect(Collectors.groupingBy(AgentIterData::getIterNum));
        return new ResultDataImpl(resultMap);
    }

    private String joinCoordinates(Coordinate coordinate){
        return coordinate.x + "," + coordinate.y;
    }
}
