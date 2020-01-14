package ru.nachos.db.repository.fire;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

public interface IntersectionFilterRepository {
    Geometry findIntersectionsByGeometry(LineString geometry, LineString geom);
}
