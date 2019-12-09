package ru.nachos.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.nachos.web.models.ResultDataImpl;

import java.util.List;
import java.util.TreeMap;

@JsonDeserialize(as = ResultDataImpl.class)
public interface ResultData {
    int getIterCount();
    TreeMap<Integer, List<AgentIterData>> getAgents();

    int getFirstIterationNumber();

    List<AgentIterData> getFirstIteration();
    List<AgentIterData> getIterationByNumber(int number);
}
