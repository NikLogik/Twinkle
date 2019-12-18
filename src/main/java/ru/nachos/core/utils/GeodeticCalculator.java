package ru.nachos.core.utils;

import com.vividsolutions.jts.geom.Coordinate;
import org.apache.commons.math.util.FastMath;
import ru.nachos.core.network.lib.PolygonV2;

public class GeodeticCalculator {

    /**
     * Решает прямую геодезическую задачу, вычисляя координаты искомой точки по исходной точке, расстоянию до искомой точки и
     * <a href='https://ru.wikipedia.org/wiki/%D0%94%D0%B8%D1%80%D0%B5%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D1%83%D0%B3%D0%BE%D0%BB'>дирекционному углу</a>
     * @param startPoint - координата исходной точки
     * @param length - расстояние от исходной точки до искомой
     * @param direction - дирекционный угол от оси X до трезка между исходной и искомой точками (по часовой стрелке)
     * @return рассчитанная точка
     */
    public static Coordinate directProblem(Coordinate startPoint, double length, double direction){
        //определяем приращение координат
        double angleRadians = Math.toRadians(direction);
        double dX = length * Math.sin(angleRadians);
        double x2 = startPoint.x + dX;
        double dY = length * Math.cos(angleRadians);
        double y2 = startPoint.y + dY;
        return new Coordinate(x2, y2);
    }

    /**
     * Решает обратную геодезическую задачу, вычисляя дирекционный угол для отрезка по его заданным вершинам
     * @param start - координата начала отрезка
     * @param end - координата конца отрезка
     * @return прямой дирекционный угол отрезка
     */
    public static double reverseProblem(Coordinate start, Coordinate end){
        double dX = end.x - start.x;
        double dY = end.y - start.y;
        double atan = FastMath.atan(dX / dY);
        double rumb = FastMath.toDegrees(Math.abs(atan));

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

    public static Coordinate[] findNearestLine(Coordinate coordinate, PolygonV2 polygonV2){
        Coordinate c1 = null;
        Coordinate c2 = null;
        Coordinate[] exteriorRing = polygonV2.getExteriorRing().getCoordinates();
        double tempHeight = Double.MAX_VALUE;
        for (int i=0; i < exteriorRing.length-1; i++){
            double s1 = exteriorRing[i].distance(coordinate);
            double s2 = exteriorRing[i+1].distance(coordinate);
            double s3 = exteriorRing[i].distance(exteriorRing[i+1]);
            double hP = (s1 + s2 + s3) / 2;
            double height = (2 / s3) * Math.sqrt(hP * (hP-s1) * (hP-s2) * (hP-s3));
            if (height < tempHeight){
                c1 = exteriorRing[i];
                c2 = exteriorRing[i+1];
                tempHeight = height;
            }
        }
        return new Coordinate[]{c1, c2};
    }

    public static double convertDirection(double direction){
        double var = direction;
        if (var < 180.000){
            var += 180.000;
        } else  if (var >= 180.000){
            var -= 180.000;
        }
        return var;
    }

    /**
     * Метода находит точку пересечения отрезка со стороной полигона.
     * @param coord1 - start point of line segment
     * @param coord2 - end point of line segment
     * @param polygonV2 - geometry, where intersection finds
     * @return Array coordinates, where:
     * <p>0 - intersection point</p>
     * <p>1, 2 - polygon segment, where intersection point is placed</p>
     */
    public static Coordinate[] findCrossPointWithPolygon(Coordinate coord1, Coordinate coord2, PolygonV2 polygonV2){
        Coordinate[] exteriorRing = polygonV2.getExteriorRing().getCoordinates();
        if (coord1.x < coord2.x) {
            double tmp = coord1.x;
            coord1.x = coord2.x;
            coord2.x = tmp;
        }
        if (coord1.y < coord2.y){
            double tmp = coord1.y;
            coord1.y = coord2.y;
            coord2.y = tmp;
        }
        double a1 = coord1.y - coord2.y;
        double b1 = coord1.x - coord2.x;
        double a2, b2, d, c1, c2;
        double xi = 0.0;
        double yi = 0.0;
        Coordinate start = null;
        Coordinate end = null;
        // параметры отрезков
        for (int i=0; i<exteriorRing.length-1; i++){
            a2 = exteriorRing[i].y - exteriorRing[i+1].y;
            b2 = exteriorRing[i].x - exteriorRing[i+1].x;
            d = a1 * b2 - a2 * b1;
            if (d!=0){
                c1 = coord2.y * coord1.x - coord2.x * coord1.y;
                c2 = exteriorRing[i+1].y * exteriorRing[i].x - exteriorRing[i+1].x * exteriorRing[i].y;
                // координаты точки пересечения
                xi = (b1 * c2 - b2 * c1) / d;
                yi = (a2 * c1 - a1 * c2) / d;
                start = exteriorRing[i];
                end = exteriorRing[i+1];
            }
        }
        return new Coordinate[]{new Coordinate(xi, yi), start, end};
    }

    /**
     * Calculate middle coordinate - center point of line segment between two source points
     * @param first
     * @param second
     * @return  - middle coordinate
     */
    public static Coordinate middleCoordinate(Coordinate first, Coordinate second) {
        double nX = (first.x + second.x) / 2;
        double nY = (first.y + second.y) / 2;
        return new Coordinate(nX, nY);
    }

    /**
     * Calculate median of triangle from the length three sides to side 'c'
     * @param a - length side a
     * @param b - length side b
     * @param c - length side c
     * @return length median from vertex of triangle to side 'c'
     */
    public static double median(double a, double b, double c){
        double var = (2 * Math.pow(a, 2)) + (2 * Math.pow(b, 2)) - Math.pow(c, 2);
        return Math.sqrt(var/4);
    }

    public static double distance(Coordinate first, Coordinate second){
        double dX = Math.pow(first.x - second.x, 2);
        double dY = Math.pow(first.y - second.y, 2);
        return Math.sqrt(dX + dY);
    }

    /**
     * Calculate closest coordinate on line for arbitrary point
     * @param start - start point of line segment
     * @param end - end point of line segment
     * @param vertex - point opposite line segment
     * @param direction
     * @return {@link Coordinate} - closest point on line segment for vertex
     */
    public static Coordinate closestPoint(Coordinate start, Coordinate end, Coordinate vertex, double direction){
        //находим оринетацию линии в координатной системе (дирекционный угол к линии)
        double side = height(start, end, vertex);
        double hypotenuse = start.distance(vertex);
        double var = Math.pow(hypotenuse, 2) - Math.pow(side, 2);
        //длина стороны внутреннего треугольника, состоящего из точек: start, vertex и точки пересечения высоты большего треугольника с его основанием (start, end)
        double side2 = Math.sqrt(var);
        Coordinate coordinate = directProblem(start, side2, direction);
        return coordinate;
    }

    /**
     * Calculate triangle height for side between points v1 and v2. v3 is vertex opposite base.
     * @param v1
     * @param v2
     * @param v3
     * @return triangle height
     */
    public static double height(Coordinate v1, Coordinate v2, Coordinate v3){
        double s1 = v1.distance(v3);
        double s2 = v2.distance(v3);
        double s3 = v1.distance(v2);
        double hP = (s1 + s2 + s3) / 2;
        double height = (2 / s3) * Math.sqrt(hP * (hP-s1) * (hP-s2) * (hP-s3));
        return height;
    }

    public static boolean crossingSegment(Coordinate p1, Coordinate p2, Coordinate p3, Coordinate p4){
        double v1, v2, v3, v4;
        v1 = findVector(p4.x - p3.x, p4.y - p3.y, p1.x - p3.x, p1.y - p3.y);
        v2 = findVector(p4.x - p3.x, p4.y - p3.y, p2.x - p3.x, p2.y - p3.y);
        v3 = findVector(p2.x - p1.x, p2.y - p1.y, p3.x - p1.x, p3.y - p1.y);
        v4 = findVector(p2.x - p1.x, p2.y - p1.y, p4.x - p1.x, p4.y - p1.y);
        if (v1*v2 < 0 && v3*v4 < 0){
            return true;
        } else{
            return false;
        }
    }

    private static double findVector(double x1, double y1, double x2, double y2){
        double vector = (x1 * y2) - (x2 * y1);
        return vector;
    }
}
