package ru.nachos.core.network;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.network.lib.*;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.model.osm.PolygonOsmModel;

import java.util.LinkedList;

final class NetworkFactoryImpl implements NetworkFactory {

    private static final GeometryFactory factory = new GeometryFactory();

    @Override
    public GeometryFactory getGeomFactory() {
        return factory;
    }

    @Override
    public PolygonV2 createPolygon(String id, PolygonOsmModel model){
        PolygonV2 polygonV2 = null;
        Geometry way = model.getWay();
        polygonV2 = new PolygonV2Impl(Id.createPolygonId(id), factory.createLinearRing(((Polygon)way).getExteriorRing().getCoordinates()), null, factory);
        setPolygonType(polygonV2, model);
        return polygonV2;
    }

    @Override
    public PolygonV2 createPolygon(String id, Polygon polygon, PolygonOsmModel model){
        PolygonV2 polygonV2 = new PolygonV2Impl(Id.createPolygonId(id), factory.createLinearRing(polygon.getExteriorRing().getCoordinates()), null, factory);
        setPolygonType(polygonV2, model);
        return polygonV2;
    }

    private void setPolygonType(PolygonV2 polygonV2, PolygonOsmModel model){
        PolygonType polygonType = PolygonType.valueOfType(model.getNatural());
        String water = model.getWater();
        if (water != null && !water.isEmpty()) {
            polygonType = PolygonType.WATER;
        }
        String waterway = model.getWaterway();
        if (waterway != null && !waterway.isEmpty()) {
            polygonType = PolygonType.WATER;
        }
        if (polygonType.getParam().equals(PolygonType.DEFAULT.getParam())) {
            String landuse = model.getLanduse();
            polygonType = PolygonType.valueOfLanduse(landuse);
        }
        polygonV2.setType(polygonType);
    }

    @Override
    public PolygonV2 createPolygon(long id, PolygonOsmModel model){
        return createPolygon(String.valueOf(id), model);
    }

    @Override
    public Node createNode(Id<Node> id, Coordinate coordinate, double elevation){
        return new NodeImpl(id, elevation, coordinate);
    }

    @Override
    public Trip createTrip(Id<Agent> id, LinkedList<Node> nodes){
        return new TripImpl(id, nodes);
    }

    @Override
    public Trip createTrip(Id<Agent> id) {
        return new TripImpl(id);
    }

    @Override
    public Link createLink(Id<Link> id, Node fromNode, Node toNode, double kRelief, int flowTime){
        return new LinkImpl(id, fromNode, toNode, kRelief, flowTime);
    }
}
