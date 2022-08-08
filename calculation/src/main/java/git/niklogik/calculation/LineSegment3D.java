package git.niklogik.calculation;

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
}
