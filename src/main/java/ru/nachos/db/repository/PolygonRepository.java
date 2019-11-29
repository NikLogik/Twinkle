package ru.nachos.db.repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.stereotype.Component;
import ru.nachos.db.OsmDatabaseManager;

import java.util.Map;

@Component
public interface PolygonRepository extends HasSRID{

    @Deprecated
    OsmDatabaseManager getManager();

    Polygon getPolygonById(long id);

    Polygon getPolygonTypeByCoordinate(Coordinate coordinate);

    Map<String, Polygon> getPolygonsFromBoundaryBox(Geometry polygon);
}
