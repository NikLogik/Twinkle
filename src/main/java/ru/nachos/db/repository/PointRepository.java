package ru.nachos.db.repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

import java.util.Map;

public interface PointRepository extends HasSRID{

    Point getPointById(long id);

    Point getNearestPoint(Coordinate coordinate);

    Map<String, Point> getPointsFromBoundaryBox(Coordinate[] coordinates);
}
