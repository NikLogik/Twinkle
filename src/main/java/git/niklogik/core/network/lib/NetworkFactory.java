package git.niklogik.core.network.lib;


import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.db.entities.osm.PolygonOsmModel;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.LinkedList;

public interface NetworkFactory {

    GeometryFactory getGeomFactory();

    PolygonV2 createPolygon(String id, PolygonOsmModel model);

    PolygonV2 createPolygon(String id, Polygon polygon, PolygonOsmModel model);

    PolygonV2 createPolygon(long id, PolygonOsmModel model);

    Node createNode(Id<Node> id, Coordinate coordinate, double elevation);

    Trip createTrip(Id<Agent> id, LinkedList<Node> nodes);

    Trip createTrip(Id<Agent> id);

    Link createLink(Id<Link> id, Node fromNode, Node toNode, double kRelief, int flowTime);
}
