package ru.nachos.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.web.models.lib.AgentIterData;

import java.io.Serializable;

public class AgentIterDataImpl implements AgentIterData, Serializable {

    @JsonIgnore
    private int iterNum;
    @JsonIgnore
    private String id;
    private Coordinate coordinates;
    public AgentIterDataImpl(int iterNum, String id, Coordinate coordinates){
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
    public Coordinate getCordinates() { return this.coordinates; }
    @Override
    public void setCoordinates(Coordinate coordinates) { this.coordinates = coordinates; }
}
