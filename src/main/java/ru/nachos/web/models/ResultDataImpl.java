package ru.nachos.web.models;

import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.ResultData;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResultDataImpl implements ResultData, Serializable {

    private Map<Integer, List<AgentIterData>> agents;

    public ResultDataImpl(Map<Integer, List<AgentIterData>> agents) {
        this.agents = agents;
    }

    @Override
    public Map<Integer, List<AgentIterData>> getAgents() { return this.agents; }
}
