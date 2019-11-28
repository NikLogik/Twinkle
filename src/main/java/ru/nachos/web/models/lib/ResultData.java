package ru.nachos.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.nachos.web.models.ResultDataImpl;

import java.util.List;
import java.util.Map;

@JsonDeserialize(as = ResultDataImpl.class)
public interface ResultData {

    Map<Integer, List<AgentIterData>> getAgents();

}
