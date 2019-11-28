package ru.nachos.web.models;

import ru.nachos.web.models.lib.AgentIterData;

import java.io.Serializable;

public class AgentIterDataImpl implements AgentIterData, Serializable {

    private int iterNum;
    private String id;
    private String coordinates;
    public AgentIterDataImpl(int iterNum, String id, String coordinates){
        this.iterNum = iterNum;
        this.id = id;
        this.coordinates = coordinates;
    }
    @Override
    public int getIterNum() { return this.iterNum; }
    @Override
    public String getAgentId() {
        return this.id;
    }
    @Override
    public String getCordinates() {
        return this.coordinates;
    }
}
