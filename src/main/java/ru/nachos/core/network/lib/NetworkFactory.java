package ru.nachos.core.network.lib;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.db.entities.osm.PolygonOsmModel;

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
