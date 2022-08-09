package git.niklogik.geo;

import git.niklogik.geo.Point3D;
import org.apache.commons.math3.util.FastMath;
import org.locationtech.jts.geom.Coordinate;

public class GeodesicPoint extends Point3D {
    public GeodesicPoint(Double elevation, Coordinate coordinate) {
        super(elevation, coordinate);
    }

    /**
     * Решает прямую геодезическую задачу, вычисляя координаты искомой точки расстоянию до искомой точки и дирекционному углу
     * @param length расстояние от исходной точки до искомой
     * @param directionAngle дирекционный угол (min 0.0 - max 360.0) от оси X до отрезка между исходной и искомой точками (по часовой стрелке)
     * @return рассчитанная точка
     */
    public Point3D pointAt(double length, double directionAngle){
        double angleRadians = Math.toRadians(directionAngle);
        double dX = length * Math.sin(angleRadians);
        double x2 = this.coordinate.x + dX;
        double dY = length * Math.cos(angleRadians);
        double y2 = this.coordinate.y + dY;
        return new Point3D(this.elevation, new Coordinate(x2, y2));
    }

    /**
     * Решает обратную геодезическую задачу, вычисляя дирекционный угол для отрезка по его заданным вершинам.
     * Начало отрезка - текущая точка.
     * @param point - координата конца отрезка
     * @return прямой дирекционный угол отрезка
     */
    public Double directionalAngle(Point3D point){
        double dX = point.coordinate.x - this.coordinate.x;
        double dY = point.coordinate.y - this.coordinate.y;
        double aTangent = FastMath.atan(dX / dY);
        double rumb = FastMath.toDegrees(Math.abs(aTangent));

        double result = 0.0;
        if (dX >= 0.000 && dY >= 0.000){
            result = rumb;
        } else if (dX < 0.000 && dY >= 0.000){
            result = 180.00 - rumb;
        } else if (dX < 0.000 && dY < 0.000){
            result = rumb - 180.00;
        } else if (dX >= 0.000 && dY < 0.000){
            result = 360.00 - rumb;
        }
        return result;
    }

    public Point3D middle(Point3D toPoint){
        double nX = (this.coordinate.x + toPoint.coordinate.x) / 2;
        double nY = (this.coordinate.y + toPoint.coordinate.y) / 2;
        double nZ = (this.coordinate.z + toPoint.coordinate.z) / 2;
        return new Point3D(nZ, new Coordinate(nX, nY));
    }
}
