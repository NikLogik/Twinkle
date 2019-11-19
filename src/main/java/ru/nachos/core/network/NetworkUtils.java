package ru.nachos.core.network;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;

import java.util.Collection;
import java.util.Map;

public final class NetworkUtils {

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

    public static boolean isInsidePolygon(Network network, PolygonV2 polygon, Coordinate coord){
        Point point = network.getFactory().getGeomFactory().createPoint(coord);
        return polygon.contains(point);
    }

    /**
     * This method find a polygon in the network, to which the point are belong
     * @param coord - the points, which belongs the polygon
     * @return ID polygon
     */
    public static Id<PolygonV2> findPolygonByAgentCoords(Network network, Coordinate coord){
        Collection<? extends PolygonV2> polygons = network.getPolygones().values();
        Id<PolygonV2> id = null;
        for (PolygonV2 poly : polygons){
            boolean contains = NetworkUtils.isInsidePolygon(network, poly, coord);
            if (contains){
                id = poly.getId();
            }
        }
        return id;

    }

    public static Coordinate findCrossPointWithPolygon(Coordinate coord1, Coordinate coord2, PolygonV2 polygonV2){
        Coordinate[] exteriorRing = polygonV2.getExteriorRing().getCoordinates();
        double a1 = coord1.y - coord2.y;
        double b1 = coord1.x - coord2.x;
        double a2, b2, d, c1, c2;
        double xi = 0.0;
        double yi = 0.0;
        // параметры отрезков
        for (int i=0; i<exteriorRing.length-1; i++){
            a2 = exteriorRing[i].y - exteriorRing[i+1].y;
            b2 = exteriorRing[i].x - exteriorRing[i+1].x;
            d = a1 * b2 - a2 * b1;
            if (d!=0){
                c1 = coord2.y * coord1.x - coord2.x * coord1.y;
                c2 = exteriorRing[i+1].y * exteriorRing[i].x - exteriorRing[i+1].x * exteriorRing[i].y;

                // координаты точки пересечения
                xi = (b1 * c2 - b2 * c1) / d;
                yi = (a2 * c1 - a1 * c2) / d;
            }
        }
        return new Coordinate(xi, yi);
    }

    public static Network createNetwork(Network network, Coordinate[] boundaryBox){
        throw new UnsupportedOperationException("Release this method");
    }

    public static Coordinate[] calculateBoundaryBox(Coordinate center, double distance){
        Coordinate[] boundaryBox = new Coordinate[4];
        boundaryBox[0] = new Coordinate(center.x + distance, center.y); //max X longitude (долгота)
        boundaryBox[1] = new Coordinate(center.x, center.y - distance); //min Y latitude (широта)
        boundaryBox[2] = new Coordinate(center.x - distance, center.y); //min X longitude (долгота)
        boundaryBox[3] = new Coordinate(center.x, center.y + distance); //max Y latitude (широта
        return boundaryBox;
    }
}
