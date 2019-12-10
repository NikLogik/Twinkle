package ru.nachos.db.repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.PolygonV2;

import java.util.List;

public interface PolygonRepository extends HasSRID{

    Polygon getPolygonById(long id);

    Polygon getPolygonByCoordinate(NetworkFactory factory, Coordinate coordinate);

    List<PolygonV2> getPolygonsFromBoundaryBox(NetworkFactory factory, Geometry geometry);

    List<String> getNaturalTypes();
}
