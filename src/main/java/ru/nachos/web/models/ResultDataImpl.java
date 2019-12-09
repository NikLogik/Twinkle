package ru.nachos.web.models;

import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.ResultData;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public class ResultDataImpl implements ResultData, Serializable {

    private int iterCount;
    private TreeMap<Integer, List<AgentIterData>> agents;

    public ResultDataImpl(int iterCount, TreeMap<Integer, List<AgentIterData>> agents) {
        this.iterCount = iterCount;
        this.agents = agents;
    }
    @Override
    public int getIterCount() { return this.iterCount; }
    @Override
    public TreeMap<Integer, List<AgentIterData>> getAgents() { return this.agents; }
    @Override
    public int getFirstIterationNumber(){ return agents.firstKey(); }
    @Override
    public List<AgentIterData> getFirstIteration(){ return agents.get(agents.firstKey()); }
    @Override
    public List<AgentIterData> getIterationByNumber(int number){ return this.agents.get(number); }


}