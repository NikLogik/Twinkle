package ru.nachos.core.network;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.PolygonRepositoryImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public final class NetworkUtils {

//    private static Logger logger = Logger.getLogger(Network.class);

    public static TreeMap<Id<PolygonV2>, Long> networkCashe = new TreeMap<>();

    private NetworkUtils(){}

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

    public static void setPolygonesToAgents(Network network, Map<Id<Agent>, Agent> agents){
//        logger.info("================= Start find geometries for agents =================");
        for(Agent agent : agents.values()){
            PolygonV2 polygon = repository.getPolygonByCoordinate(network.getFactory(), agent.getCoordinate());
            if (polygon == null){
//                logger.warn("Geometry for agent id=" + agent.getId() + " not found in database");
            }
            agent.setPolygonId(polygon.getId());
            network.getPolygones().get(polygon.getPolygonType()).put(polygon.getId(), polygon);
        }
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

    public static Coordinate findIntersectionPoint(Coordinate start, Coordinate end, PolygonV2 polygonV2){
        Coordinate intersectionPoint = repository.getIntersectionPoint(new Coordinate[]{start, end}, polygonV2);
        if (intersectionPoint == null){
            return end;
        } else {
            return intersectionPoint;
        }
    }

    public static Network createNetwork(Network network, Coordinate[] boundaryBox){
        Polygon polygon = network.getFactory().getGeomFactory().createPolygon(new Coordinate[]{boundaryBox[0], boundaryBox[1], boundaryBox[2], boundaryBox[3], boundaryBox[0]});
        List<PolygonV2> polygonsFromBoundaryBox = repository.getPolygonsFromBoundaryBox(network.getFactory(), polygon);
        Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> collect = polygonsFromBoundaryBox.stream().collect(Collectors.groupingBy(PolygonV2::getPolygonType, Collectors.toMap(PolygonV2::getId, Function.identity())));
        network.getPolygones().putAll(collect);
//        logger.info("Finished creating network.");
        return network;
    }

    public static Coordinate[] findNearestLine(Coordinate coordinate, PolygonV2 polygonV2){
        Coordinate c1 = null;
        Coordinate c2 = null;
        Coordinate[] exteriorRing = polygonV2.getExteriorRing().getCoordinates();
        double tempHeight = Double.MAX_VALUE;
        for (int i=0; i < exteriorRing.length-1; i++){
            double s1 = exteriorRing[i].distance(coordinate);
            double s2 = exteriorRing[i+1].distance(coordinate);
            double s3 = exteriorRing[i].distance(exteriorRing[i+1]);
            double hP = (s1 + s2 + s3) / 2;
            double height = (2 / s3) * Math.sqrt(hP * (hP-s1) * (hP-s2) * (hP-s3));
            if (height < tempHeight){
                c1 = exteriorRing[i];
                c2 = exteriorRing[i+1];
                tempHeight = height;
            }
        }
        return new Coordinate[]{c1, c2};
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
