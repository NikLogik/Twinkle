package ru.nachos.web.models;

import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.ResultData;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public class ResultDataImpl implements ResultData, Serializable {

    private TreeMap<Integer, List<AgentIterData>> agents;

    public ResultDataImpl(TreeMap<Integer, List<AgentIterData>> agents) {
        this.agents = agents;
        System.out.println("<===================== New instance " + this.hashCode() + " ============================>");
    }

    @Override
    public TreeMap<Integer, List<AgentIterData>> getAgents() { return this.agents; }

    @Override
    public List<AgentIterData> getFirstEntry(){ return this.agents.get(agents.firstKey()); }
}