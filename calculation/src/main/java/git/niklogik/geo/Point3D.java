package git.niklogik.geo;

import org.locationtech.jts.geom.Coordinate;

public class Point3D {
    public final Double elevation;
    public final Coordinate coordinate;

    public Point3D(Double elevation, Coordinate coordinate) {
        this.elevation = elevation;
        this.coordinate = coordinate;
    }

    public Double distance(Point3D point){
        return this.coordinate.distance(point.coordinate);
    }

    public Double dHeight(Point3D point){
        return point.elevation - this.elevation;
    }
}
