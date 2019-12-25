package ru.nachos.core.network;


import com.vividsolutions.jts.geom.*;
import org.springframework.stereotype.Service;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;

import java.util.*;
import java.util.stream.Collectors;

@Service
public final class NetworkUtils {

//    private static Logger logger = Logger.getLogger(Network.class);

    public static TreeMap<Id<PolygonV2>, Long> networkCashe = new TreeMap<>();

    private NetworkUtils(){}

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
        return findPolygonByAgentCoords(geomFactory, network.getPolygones(), coord);
    }

    public static PolygonV2 findPolygonByAgentCoords(GeometryFactory geoFactory,
                                       Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons, Coordinate coordinate)
    {
        List<Collection<PolygonV2>> list = polygons.values().stream().map(Map::values).collect(Collectors.toList());
        PolygonV2 polygon = null;
        for (Collection<PolygonV2> var : list){
            for (PolygonV2 polygonV2 : var){
                if (isInsidePolygon(geoFactory, polygonV2, coordinate)){
                    polygon = polygonV2;
                }
            }
        }
        return polygon;
    }

    public static Coordinate findIntersectionPoint(Coordinate start, Coordinate end, PolygonV2 polygonV2, GeometryFactory geomFactory){
        LineString lineString = geomFactory.createLineString(new Coordinate[]{start, end});
        Geometry geometry = lineString.intersection(polygonV2);
        Coordinate position = null;
        if (geometry != null){
            double distance = start.distance(end);
            for (Coordinate coordinate : geometry.getCoordinates()){
                if (start.distance(coordinate) < distance){
                    position = coordinate;
                    distance = start.distance(coordinate);
                }
            }
            if (position != null){
                return position;
            }
            else {
                return end;
            }
        } else {
            return end;
        }
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
