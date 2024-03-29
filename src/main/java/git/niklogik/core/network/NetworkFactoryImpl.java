package git.niklogik.core.network;

import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.NetworkFactory;
import git.niklogik.core.network.lib.Node;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.network.lib.Trip;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.utils.PolygonType;
import git.niklogik.db.entities.osm.PolygonOsmModel;

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
