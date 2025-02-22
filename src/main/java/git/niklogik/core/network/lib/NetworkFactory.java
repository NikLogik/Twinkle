package git.niklogik.core.network.lib;


import git.niklogik.db.entities.osm.PolygonOsmModel;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.UUID;

public interface NetworkFactory {

    GeometryFactory getGeomFactory();

    PolygonV2 createPolygon(String id, PolygonOsmModel model);

    PolygonV2 createPolygon(String id, Polygon polygon, PolygonOsmModel model);

    PolygonV2 createPolygon(Long id, PolygonOsmModel model);

    Node createNode(Coordinate coordinate, Double elevation);

    Trip createTrip(UUID id, LinkedList<Node> nodes);

    Link createLink(Node fromNode, Node toNode, BigDecimal kRelief, Integer flowTime);
}
