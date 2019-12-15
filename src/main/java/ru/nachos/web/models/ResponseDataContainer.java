package ru.nachos.web.models;

import ru.nachos.web.models.lib.ResponseData;

import java.util.Map;
import java.util.TreeMap;

public class ResponseDataContainer {

    private int iterCount;
    private TreeMap<Integer, ResponseData> agents;

    public ResponseDataContainer(TreeMap<Integer, ResponseData> agents) {
        this.iterCount = agents.size();
        this.agents = agents;
    }
    public int getIterCount() { return this.iterCount; }
    public TreeMap<Integer, ResponseData> getAgents() { return agents; }
    public Map.Entry<Integer, ResponseData> getFist(){ return agents.firstEntry(); }
    public ResponseData getResponseForIter(int iterNum){ return agents.get(iterNum); }
    public void reset(){
        iterCount = 0;
        agents.clear();
    }
}