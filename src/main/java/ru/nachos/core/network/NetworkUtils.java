package ru.nachos.core.network;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.PolygonRepositoryImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public final class NetworkUtils {

    private NetworkUtils(){}

    @Deprecated
    public static PolygonRepositoryImpl getRepository() {
        return repository;
    }

    private static PolygonRepositoryImpl repository;

    @Autowired
    public void setPolygonRepository(PolygonRepositoryImpl repository){
        NetworkUtils.repository = repository;
    }

    public static Network createNetwork(){
        return new NetworkImpl(new NetworkFactoryImpl());
    }

    public static int getHeightPolygon(Map<Id<PolygonV2>, ? extends PolygonV2> map, Id<PolygonV2> id){
        PolygonV2 polygon = map.get(id);
        return polygon.getHeight();
    }

    /**
     * This method is defines, does polygon contains the target point or not
     * @param polygon target polygon
     * @param point checking point
     * @return true, if target point is situated in the checking polygon, otherwise false
     */
    public static boolean isInsidePolygon(PolygonV2 polygon, Point point){
        return polygon.contains(point);
    }

    public static boolean isInsidePolygon(GeometryFactory factory, PolygonV2 polygon, Coordinate coord){
        Point point = factory.createPoint(coord);
        return polygon.contains(point);
    }

    /**
     * This method find a polygon in the network, to which the point are belong
     * @param network - network, where will find polygon for point
     * @param coord - the points, which belongs the polygon
     * @return Polygon, if network contains its or null
     */
    public static PolygonV2 findPolygonByAgentCoords(Network network, Coordinate coord){
        GeometryFactory geomFactory = network.getFactory().getGeomFactory();
        List<Collection<PolygonV2>> polygons = network.getPolygones().values().stream().map(Map::values).collect(Collectors.toList());
        PolygonV2 polygon = null;
        for (Collection<PolygonV2> list : polygons){
            for (PolygonV2 polygonV2 : list){
                if (isInsidePolygon(geomFactory, polygonV2, coord)){
                    polygon = polygonV2;
                }
            }
        }
        return polygon;
    }

    public static Network createNetwork(Network network, Coordinate[] boundaryBox){
        Polygon polygon = network.getFactory().getGeomFactory().createPolygon(new Coordinate[]{boundaryBox[0], boundaryBox[1], boundaryBox[2], boundaryBox[3], boundaryBox[0]});
        List<PolygonV2> polygonsFromBoundaryBox = repository.getPolygonsFromBoundaryBox(network.getFactory(), polygon);
        Map<PolygonType, List<PolygonV2>> collect = polygonsFromBoundaryBox.stream().collect(Collectors.groupingBy(PolygonV2::getPolygonType));
        Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> typeMap = new HashMap<>();
        for (Map.Entry<PolygonType, List<PolygonV2>> entry : collect.entrySet()){
            Map<Id<PolygonV2>, PolygonV2> var = entry.getValue().stream().collect(Collectors.toMap(key->key.getId(),value->value));
            typeMap.put(entry.getKey(), var);
        }
        network.getPolygones().putAll(typeMap);
        return network;
    }

    public static Coordinate[] calculateBoundaryBox(Coordinate center, double distance){
        Coordinate[] boundaryBox = new Coordinate[4];
        boundaryBox[0] = new Coordinate(center.x + distance, center.y); //max X longitude (долгота)
        boundaryBox[1] = new Coordinate(center.x, center.y - distance); //min Y latitude (широта)
        boundaryBox[2] = new Coordinate(center.x - distance, center.y); //min X longitude (долгота)
        boundaryBox[3] = new Coordinate(center.x, center.y + distance); //max Y latitude (широта
        return boundaryBox;
    }

    public static Coordinate centerLine(Coordinate coord1, Coordinate coord2){
        double xx;
        double yy;
        xx = 0.5D * (coord1.x + coord2.x);
        yy = 0.5D * (coord1.y + coord2.y);
        return new Coordinate(xx, yy);
    }
}
