package ru.nachos.core.utils;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.util.LineStringExtracter;
import com.vividsolutions.jts.operation.polygonize.Polygonizer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JtsTools {

    private static Logger logger = Logger.getLogger(JtsTools.class);

    public static Geometry polygonize(Geometry geometry) {
        List lines = LineStringExtracter.getLines(geometry);
        Polygonizer polygonizer = new Polygonizer();
        polygonizer.add(lines);
        Collection polys = polygonizer.getPolygons();
        Polygon[] polyArray = GeometryFactory.toPolygonArray(polys);
        return geometry.getFactory().createGeometryCollection(polyArray);
    }

    public static GeometryCollection splitPolygon(Geometry poly, Coordinate p1, Coordinate p2) {
        logger.info("Start split polygon by segment line: " + p1.toString() + ", " + p2.toString());
        GeometryFactory factory = new GeometryFactory();
        LineString lineString = factory.createLineString(new Coordinate[]{p1, p2});
        Geometry nodedLinework = poly.getBoundary().union(lineString);
        Geometry polys = polygonize(nodedLinework);

        // Only keep polygons which are inside the input
        List output = new ArrayList();
        for (int i = 0; i < polys.getNumGeometries(); i++) {
            Polygon candpoly = (Polygon) polys.getGeometryN(i);
            if (poly.contains(candpoly.getInteriorPoint())) {
                output.add(candpoly);
            }
        }
        return poly.getFactory().createGeometryCollection(GeometryFactory.toGeometryArray(output));
    }
}
