package ru.nachos.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.web.models.AgentIterDataImpl;

@JsonSerialize(as = AgentIterDataImpl.class)
public interface AgentIterData {
    int getIterNum();
    String getAgentId();
    Coordinate getCordinates();
    void setCoordinates(Coordinate coordinates);
}
