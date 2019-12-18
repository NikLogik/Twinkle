package ru.nachos.web.services.lib;

import org.opengis.referencing.operation.MathTransform;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.web.models.ResponseDataContainer;
import ru.nachos.web.models.lib.ResponseData;

import java.util.LinkedList;
import java.util.Map;

public interface ResponseDataService {
    void prepareResult(Map<Integer, LinkedList<AgentState>> agents, MathTransform transform);

    boolean containsIter(int iterNum);

    Map.Entry<Integer, ResponseData> getResponseByFirstIteration();
    ResponseData getResponseDataByIter(int iterNum);

    ResponseDataContainer getContainer();

    void clearData();
}
