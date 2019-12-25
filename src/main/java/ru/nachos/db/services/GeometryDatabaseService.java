package ru.nachos.db.services;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.model.osm.PolygonOsmModel;
import ru.nachos.db.repository.osm.PolygonOsmModelRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GeometryDatabaseService {

    private PolygonOsmModelRepository polygonRepository;

    @Autowired
    public GeometryDatabaseService(PolygonOsmModelRepository polygonRepository){
        this.polygonRepository = polygonRepository;
    }

    public Network createNetworkFromBoundaryBox(Network network, Coordinate[] boundaryBox){
        Polygon polygon = network.getFactory().getGeomFactory().createPolygon(new Coordinate[]{boundaryBox[0], boundaryBox[1], boundaryBox[2], boundaryBox[3], boundaryBox[0]});
        polygon.setSRID(3857);
        List<PolygonOsmModel> polygons = polygonRepository.findPolygonOsmModelsInsideWay(polygon);
        network.getPolygones().putAll(sortPolygonsByType(network.getFactory(), polygons));
        return network;
    }

    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> sortPolygonsByType(NetworkFactory factory, List<PolygonOsmModel> polygons){
        Set<Long> ids = new TreeSet<>();
        long counter = 1000;
        List<PolygonV2> polygonV2s = new ArrayList<>();
        for (PolygonOsmModel polygon : polygons){
            long id = polygon.getOsm_id();
            if (polygon.getWay() instanceof MultiPolygon) {
                for (int i=0; i<polygon.getWay().getNumGeometries(); i++){
                    polygonV2s.add(factory.createPolygon(id + ":multi-" + counter, (Polygon) polygon.getWay().getGeometryN(i), polygon));
                    counter+=100;
                }
            } else {
                if (ids.contains(id)) {
                    id += counter;
                    polygonV2s.add(factory.createPolygon(id + ":duplicate", polygon));
                    counter+=100;
                } else {
                    polygonV2s.add(factory.createPolygon(id, polygon));
                    ids.add(id);
                }
            }
        }
        return polygonV2s.stream()
                .collect(Collectors.groupingBy(PolygonV2::getPolygonType, Collectors.toMap(PolygonV2::getId, Function.identity())));
    }

    public int findSRID(){
        return polygonRepository.getSRID();
    }
}
