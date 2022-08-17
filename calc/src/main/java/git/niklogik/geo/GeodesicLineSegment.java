package git.niklogik.geo;

public class GeodesicLineSegment extends LineSegment3D {

    public GeodesicLineSegment(LineSegment3D lineSegment){ super(lineSegment.start, lineSegment.end); }

    public GeodesicLineSegment(Point3D start, Point3D end) {
        super(start, end);
    }

    /**
     * Perpendicular from the arbitrary point to the line
     * @param point3D arbitrary point
     * @return length of the normal between point and line
     */
    public Double normalLength(Point3D point3D) {
        double s1 = start.distance(point3D);
        double s2 = end.distance(point3D);
        double s3 = this.length();
        double hP = (s1 + s2 + s3) / 2;
        return  (2 / s3) * Math.sqrt(hP * (hP-s1) * (hP-s2) * (hP-s3));
    }

    /**
     * Median from arbitrary point to current segment
     * in the triangle which formed by current line segment and additional vertex
     * @param point3D additional vertex
     * @return median length
     */
    public Double medianLength(Point3D point3D) {
        double a = start.distance(point3D);
        double b = end.distance(point3D);
        double var = (2 * Math.pow(a, 2)) + (2 * Math.pow(b, 2)) - Math.pow(length(), 2);
        return Math.sqrt(var/4);
    }
}
