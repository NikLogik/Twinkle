package ru.nachos.db.repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.stereotype.Component;

import java.util.Map;

public interface PolygonRepository extends HasSRID{

    Polygon getPolygonById(long id);

    Polygon getPolygonByCoordinate(Coordinate coordinate);

    Map<String, Polygon> getPolygonsFromBoundaryBox(Geometry polygon);
}
