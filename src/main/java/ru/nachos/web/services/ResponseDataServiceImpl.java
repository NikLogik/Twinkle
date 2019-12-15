package ru.nachos.web.services;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseDataContainer;
import ru.nachos.web.models.ResponseDataImpl;
import ru.nachos.web.models.lib.ResponseData;
import ru.nachos.web.services.lib.ResponseDataService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class ResponseDataServiceImpl implements ResponseDataService {

    private ResponseDataContainer container;
    private MathTransform transform;

    @Override
    public void prepareResult(Map<Integer, AgentMap> agents, MathTransform transform) {
        this.transform = transform;
        TreeMap<Integer, ResponseData> responseMap = new TreeMap<>();
        ResponseData data;
        for (Map.Entry<Integer, AgentMap> entry : agents.entrySet()){
            LinkedList<CoordinateJson> coordinates = new LinkedList<>();
            Iterator<Agent> iterator = entry.getValue().iterator();
            while (iterator.hasNext()){
                Agent next = iterator.next();
                if (next.getStates().keySet().contains(entry.getKey())){
                    Coordinate coordinate = next.getStateByIter(entry.getKey()).getCoord();
                    coordinates.add(transform(coordinate));
                }
            }
            CoordinateJson[] coordinateJsons = coordinates.toArray(new CoordinateJson[entry.getValue().size()]);
            responseMap.put(entry.getKey(), new ResponseDataImpl(agents.keySet().size(), entry.getKey(), coordinateJsons));
        }
        container = new ResponseDataContainer(responseMap);
    }

    private CoordinateJson transform(Coordinate coordinate){
        CoordinateJson json = null;
        try {
            Coordinate transform = JTS.transform(coordinate, null, this.transform);
            json = new CoordinateJson(transform);
        } catch (TransformException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public ResponseData getResponseDataByIter(int iterNum) {
        return container.getResponseForIter(iterNum);
    }

    @Override
    public boolean containsIter(int iterNum){
        return container.getAgents().keySet().contains(iterNum);
    }

    @Override
    public Map.Entry<Integer, ResponseData> getResponseByFirstIteration() { return container.getFist(); }

    @Override
    public ResponseDataContainer getContainer() { return container; }

    @Override
    public void clearData(){
        container = null;
        transform = null;
    }
}
