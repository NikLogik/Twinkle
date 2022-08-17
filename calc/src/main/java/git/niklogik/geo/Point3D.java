package git.niklogik.geo;

import git.niklogik.Position;
import org.locationtech.jts.geom.Coordinate;

public class Point3D implements Position {
    public final Double elevation;
    public final Coordinate coordinate;

    public Point3D(Double elevation, Coordinate coordinate) {
        this.elevation = elevation;
        this.coordinate = coordinate;
    }

    @Override
    public double getX() {
        return this.coordinate.x;
    }

    @Override
    public double getY() {
        return this.coordinate.y;
    }

    @Override
    public double getElevation() {
        return this.elevation;
    }

    public Double distance(Point3D point){
        return this.coordinate.distance(point.coordinate);
    }

    public Double dHeight(Point3D point){
        return point.elevation - this.elevation;
    }
}
