package ru.nachos.core.network.lib;


import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import ru.nachos.db.model.osm.PolygonOsmModel;

public interface NetworkFactory {

    GeometryFactory getGeomFactory();

    PolygonV2 createPolygon(String id, PolygonOsmModel model);

    PolygonV2 createPolygon(String id, Polygon polygon, PolygonOsmModel model);

    PolygonV2 createPolygon(long id, PolygonOsmModel model);
}
