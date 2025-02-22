package git.niklogik.core.network;

import git.niklogik.core.Id;
import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.NetworkFactory;
import git.niklogik.core.network.lib.Node;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.network.lib.Trip;
import git.niklogik.core.utils.PolygonType;
import git.niklogik.db.entities.osm.PolygonOsmModel;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.UUID;

import static com.google.common.base.Strings.isNullOrEmpty;

final class NetworkFactoryImpl implements NetworkFactory {

    private static final GeometryFactory factory = new GeometryFactory();

    @Override
    public GeometryFactory getGeomFactory() {
        return factory;
    }

    @Override
    public PolygonV2 createPolygon(String id, PolygonOsmModel model) {
        PolygonV2 polygonV2 = null;
        Geometry way = model.getWay();
        polygonV2 = new PolygonV2Impl(Id.createPolygonId(id),
                                      factory.createLinearRing(((Polygon) way).getExteriorRing().getCoordinates()),
                                      null, factory);
        polygonV2.setType(getPolygonType(model));
        return polygonV2;
    }

    @Override
    public PolygonV2 createPolygon(String id, Polygon polygon, PolygonOsmModel model) {
        PolygonV2 polygonV2 = new PolygonV2Impl(Id.createPolygonId(id),
                                                factory.createLinearRing(polygon.getExteriorRing().getCoordinates()),
                                                null, factory);
        polygonV2.setType(getPolygonType(model));
        return polygonV2;
    }

    private PolygonType getPolygonType(PolygonOsmModel model) {
        if (isWaterType(model)) return PolygonType.WATER;

        return PolygonType.valueOfLanduse(model.getLanduse());
    }

    private boolean isWaterType(PolygonOsmModel model) {
        return !isNullOrEmpty(model.getWater()) || !isNullOrEmpty(model.getWaterway());
    }

    @Override
    public PolygonV2 createPolygon(Long id, PolygonOsmModel model) {
        return createPolygon(String.valueOf(id), model);
    }

    @Override
    public Node createNode(Coordinate coordinate, Double elevation) {
        return new NodeImpl(UUID.randomUUID(), elevation, coordinate);
    }

    @Override
    public Trip createTrip(UUID id, LinkedList<Node> nodes) {
        return new TripImpl(id, nodes);
    }

    @Override
    public Link createLink(Node fromNode, Node toNode, BigDecimal kRelief, Integer flowTime) {
        return new LinkImpl(UUID.randomUUID(), fromNode, toNode, kRelief, flowTime);
    }
}
