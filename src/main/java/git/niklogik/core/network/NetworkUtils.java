package git.niklogik.core.network;


import git.niklogik.core.Id;
import git.niklogik.core.fire.algorithms.FireSpreadCalculator;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.Network;
import git.niklogik.core.network.lib.NetworkFactory;
import git.niklogik.core.network.lib.Node;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.network.lib.Trip;
import git.niklogik.core.utils.AgentMap;
import git.niklogik.core.utils.GeodeticCalculator;
import git.niklogik.core.utils.PolygonType;
import git.niklogik.db.entities.fire.ContourLine;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static git.niklogik.core.utils.BigDecimalUtils.divide;
import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;

@Service
public final class NetworkUtils {

    public static TreeMap<Id<PolygonV2>, Long> networkCache = new TreeMap<>();

    private NetworkUtils() {}

    public static Network createNetwork() {
        return new NetworkImpl(new NetworkFactoryImpl());
    }

    public static int getHeightPolygon(Map<Id<PolygonV2>, ? extends PolygonV2> map, Id<PolygonV2> id) {
        PolygonV2 polygon = map.get(id);
        return polygon.getHeight();
    }

    /**
     * This method is defines, does polygon contains the target point or not
     *
     * @param polygon target polygon
     * @param point   checking point
     * @return true, if target point is situated in the checking polygon, otherwise false
     */
    public static boolean isInsidePolygon(PolygonV2 polygon, Point point) {
        return polygon.contains(point);
    }

    public static boolean isInsidePolygon(GeometryFactory factory, PolygonV2 polygon, Coordinate coord) {
        Point point = factory.createPoint(coord);
        return polygon.contains(point);
    }

    /**
     * This method find a polygon in the network, to which the point are belong
     *
     * @param network - network, where will find polygon for point
     * @param coord   - the points, which belongs the polygon
     * @return Polygon, if network contains its or null
     */
    public static PolygonV2 findPolygonByAgentCoords(Network network, Coordinate coord) {
        return findPolygonByAgentCoords(new GeometryFactory(), network.getPolygones(), coord);
    }

    public static PolygonV2 findPolygonByAgentCoords(GeometryFactory geoFactory,
                                                     Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons, Coordinate coordinate) {
        List<Collection<PolygonV2>> list = polygons.values().stream().map(Map::values).toList();
        PolygonV2 polygon = null;
        for (Collection<PolygonV2> var : list) {
            for (PolygonV2 polygonV2 : var) {
                if (isInsidePolygon(geoFactory, polygonV2, coordinate)) {
                    polygon = polygonV2;
                }
            }
        }
        return polygon;
    }

    public static Coordinate findIntersectionPoint(Coordinate start, Coordinate end, PolygonV2 polygonV2) {
        LineString lineString = new GeometryFactory().createLineString(new Coordinate[]{start, end});
        Geometry geometry = lineString.intersection(polygonV2);
        Coordinate position = null;
        if (geometry != null) {
            double distance = start.distance(end);
            for (Coordinate coordinate : geometry.getCoordinates()) {
                if (start.distance(coordinate) < distance) {
                    position = coordinate;
                    distance = start.distance(coordinate);
                }
            }
            if (position != null) {
                return position;
            } else {
                return end;
            }
        } else {
            return end;
        }
    }

    public static Coordinate[] calculateBoundaryBox(Coordinate center, double distance) {
        Coordinate[] boundaryBox = new Coordinate[4];
        boundaryBox[0] = new Coordinate(center.x + distance, center.y); //max X longitude (долгота)
        boundaryBox[1] = new Coordinate(center.x, center.y - distance); //min Y latitude (широта)
        boundaryBox[2] = new Coordinate(center.x - distance, center.y); //min X longitude (долгота)
        boundaryBox[3] = new Coordinate(center.x, center.y + distance); //max Y latitude (широта
        return boundaryBox;
    }

    public static Coordinate centerLine(Coordinate coord1, Coordinate coord2) {
        double xx;
        double yy;
        xx = 0.5D * (coord1.x + coord2.x);
        yy = 0.5D * (coord1.y + coord2.y);
        return new Coordinate(xx, yy);
    }

    public static void createTrips(Network network, AgentMap agents, long simTime, FireSpreadCalculator calculator) {
        for (Agent next : agents) {
            createTrip(next, network, simTime, 0, calculator);
        }
    }

    public static void createTrip(Agent agent, Network network, long simTime, double curTime, FireSpreadCalculator calculator) {
        NetworkFactory networkFactory = network.getFactory();
        Coordinate pS = agent.getCoordinate();
        var distance = agent.getSpeed().multiply(toBigDecimal(simTime / 30.0));
        Coordinate pF = GeodeticCalculator.directProblem(pS, distance, agent.getDirection());
        LineString lineString = new GeometryFactory().createLineString(new Coordinate[]{pS, pF});
        //!!!
        lineString.setSRID(3857);
        int counter = 0;
        LinkedList<Node> intersections = new LinkedList<>();
        for (ContourLine line : network.getRelief().values()) {
            if (lineString.intersects(line.getHorizontal())) {
                Geometry intersection = lineString.intersection(line.getHorizontal());
                Node node = networkFactory.createNode(intersection.getCoordinate(), line.getElevation());
                intersections.add(node);
                counter++;
            }
        }
        if (intersections.isEmpty()) {
            intersections.add(networkFactory.createNode(pS, 0.0));
            intersections.add(networkFactory.createNode(pF, 0.0));
        } else {
            intersections.sort((a, b) ->
                                   (int) (a.getCoordinate().distance(agent.getCoordinate()) - b.getCoordinate()
                                                                                               .distance(
                                                                                                   agent.getCoordinate())));
            intersections.addFirst(networkFactory.createNode(pS,
                                                             intersections.getFirst().getElevation()));
            intersections.addLast(networkFactory.createNode(pF,
                                                            intersections.getLast().getElevation()));
            intersections.sort((a, b) ->
                                   (int) (a.getCoordinate().distance(agent.getCoordinate()) - b.getCoordinate()
                                                                                               .distance(
                                                                                                   agent.getCoordinate())));
        }
        Trip trip = networkFactory.createTrip(agent.getId(), intersections);
        int tripTime = (int) curTime;
        for (int i = 1; i < intersections.size(); i++) {
            Node from = intersections.get(i - 1);
            Node to = intersections.get(i);
            var kRelief = agent.getSpeed();
            if (intersections.size() == 2) {
                kRelief = kRelief.multiply(BigDecimal.ZERO);
            } else {
                kRelief = kRelief.multiply(calculator.reliefCoefficient(from, to));
            }
            from.setTripTime(tripTime);

            var distanceBetween = from.getCoordinate().distance(to.getCoordinate());
            int flowTime = divide(
                toBigDecimal(distanceBetween),
                agent.getSpeed().add(kRelief))
                .multiply(toBigDecimal(60))
                .intValue();
            tripTime += flowTime;
            Link link = networkFactory.createLink(from, to,
                                                  kRelief.add(agent.getSpeed()), flowTime);
            from.setOutLink(link);
            to.setInLink(link);
            to.setTripTime(tripTime);
            trip.addLink(link);
        }
        network.addTrip(agent.getId(), trip);
    }
}
