package ru.nachos.core.network.lib;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import ru.nachos.core.Id;

import java.util.Collection;

public interface NetworkFactory {

    GeometryFactory getGeomFactory();

    PolygonV2 createPolygon(Id<PolygonV2> id, Point...externalRing);

    PolygonV2 createPolygon(Id<PolygonV2> id, Collection<Coordinate> externalRing);

    PolygonV2 createPolygon(Id<PolygonV2> id, Coordinate[] externalRing);

    Node createNode(Id<Node> id, Coordinate coordinate);

    Node createNode(Id<Node> id);

    Link createLink(Id<Link> id, Node fromNode, Node toNode);
}
