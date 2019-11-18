package ru.nachos.core.network;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Link;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.Node;
import ru.nachos.core.network.lib.PolygonV2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

final class NetworkFactoryImpl implements NetworkFactory {

    private static final GeometryFactory factory = new GeometryFactory();

    @Override
    public GeometryFactory getGeomFactory() {
        return factory;
    }

    @Override
    public PolygonV2 createPolygon(Id<PolygonV2> id, Point... externalRing) {
        List<Coordinate> coordinates = Arrays.stream(externalRing).map(Point::getCoordinate).collect(Collectors.toList());
        return createPolygon(id, coordinates);
    }

    @Override
    public PolygonV2 createPolygon(Id<PolygonV2> id, Collection<Coordinate> externalRing){
        LinearRing linearRing = factory.createLinearRing(externalRing.toArray(new Coordinate[0]));
        return new PolygonV2Impl(id, linearRing, null, factory);
    }

    @Override
    public Node createNode(Id<Node> id) { return new NodeImpl(id); }

    @Override
    public Node createNode(Id<Node> id, Coordinate coordinate) {
        return new NodeImpl(id, coordinate);
    }

    @Override
    public Link createLink(Id<Link> id, Node fromNode, Node toNode) { return new LinkImpl(id, fromNode, toNode); }
}
