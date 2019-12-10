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
import ru.nachos.db.repository.PolygonRepository;

import java.util.Arrays;
import java.util.Collection;

final class NetworkFactoryImpl implements NetworkFactory {

    private static final GeometryFactory factory = new GeometryFactory();
    private PolygonRepository repository;

    @Override
    public GeometryFactory getGeomFactory() {
        return factory;
    }

    @Override
    public PolygonV2 createPolygon(Id<PolygonV2> id, Point... externalRing) {
        Coordinate[] coordinates = Arrays.stream(externalRing).map(Point::getCoordinate).toArray(Coordinate[]::new);
        return createPolygon(id, coordinates);
    }

    @Override
    public PolygonV2 createPolygon(Id<PolygonV2> id, Collection<Coordinate> externalRing){
        return createPolygon(id, externalRing.toArray(new Coordinate[0]));
    }

    @Override
    public PolygonV2 createPolygon(Id<PolygonV2> id, Coordinate[] externalRing){
        LinearRing linearRing = factory.createLinearRing(externalRing);
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
