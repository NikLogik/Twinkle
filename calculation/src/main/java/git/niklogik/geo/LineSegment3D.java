package git.niklogik.geo;

import org.locationtech.jts.geom.Coordinate;

public class LineSegment3D {
    public final Point3D start;
    public final Point3D end;

    public LineSegment3D(Point3D start, Point3D end) {
        this.start = start;
        this.end = end;
    }

    public Double length() {
        return start.distance(end);
    }

    public Point3D middle() {
        double nX = (start.coordinate.x + end.coordinate.x) / 2;
        double nY = (start.coordinate.y + end.coordinate.y) / 2;
        double nZ = (start.coordinate.z + end.coordinate.z) / 2;
        return new Point3D(nZ, new Coordinate(nX, nY));
    }
}
