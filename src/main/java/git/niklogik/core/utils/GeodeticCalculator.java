package git.niklogik.core.utils;

import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.Node;
import git.niklogik.core.network.lib.Trip;
import org.apache.commons.math3.util.FastMath;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;
import java.util.LinkedList;

import static git.niklogik.core.utils.BigDecimalUtils.lessThan;
import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;

public class GeodeticCalculator {

    /**
     * Решает прямую геодезическую задачу, вычисляя координаты искомой точки по исходной точке, расстоянию до искомой точки и
     * <a href='https://ru.wikipedia.org/wiki/%D0%94%D0%B8%D1%80%D0%B5%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D1%83%D0%B3%D0%BE%D0%BB'>дирекционному углу</a>
     * @param startPoint - координата исходной точки
     * @param length - расстояние от исходной точки до искомой
     * @param direction - дирекционный угол от оси X до трезка между исходной и искомой точками (по часовой стрелке)
     * @return рассчитанная точка
     */
    public static Coordinate directProblem(Coordinate startPoint, BigDecimal length, BigDecimal direction){
        //определяем приращение координат
        double angleRadians = Math.toRadians(direction.doubleValue());
        var dX = length.multiply(toBigDecimal(Math.sin(angleRadians)));
        double x2 = startPoint.x + dX.doubleValue();
        var dY = length.multiply(toBigDecimal(angleRadians));
        double y2 = startPoint.y + dY.doubleValue();
        return new Coordinate(x2, y2);
    }

    /**
     * Решает обратную геодезическую задачу, вычисляя дирекционный угол для отрезка по его заданным вершинам
     * @param start - координата начала отрезка
     * @param end - координата конца отрезка
     * @return прямой дирекционный угол отрезка
     */
    public static BigDecimal reverseProblem(Coordinate start, Coordinate end){
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
        return toBigDecimal(result);
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
    public static double median(BigDecimal a, BigDecimal b, double c){
        double var = (2 * Math.pow(a.doubleValue(), 2)) + (2 * Math.pow(b.doubleValue(), 2)) - Math.pow(c, 2);
        return Math.sqrt(var/4);
    }

    public static Coordinate distance(double currentTime, Trip trip, Agent agent){
        LinkedList<Node> nodes = trip.getNodes();
        Link current = null;
        for (Node node : nodes){
            if (node.getTripTime() > currentTime){
                current = node.getInLink();
                break;
            }
        }
        double timeOnLink = (currentTime - current.fromNode().getTripTime()) / 60;
        var leftDistance = toBigDecimal(timeOnLink).multiply(current.linkSpeed());
        return directProblem(current.fromNode().getCoordinate(), leftDistance, agent.getDirection());
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
        //находим ориентацию линии в координатной системе (дирекционный угол к линии)
        double side = height(start, end, vertex);
        double hypotenuse = start.distance(vertex);
        double var = Math.pow(hypotenuse, 2) - Math.pow(side, 2);
        //длина стороны внутреннего треугольника, состоящего из точек: start, vertex и точки пересечения высоты большего треугольника с его основанием (start, end)
        double side2 = Math.sqrt(var);
        return directProblem(start, toBigDecimal(side2), toBigDecimal(direction));
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

    public static BigDecimal ortoDirection(Coordinate start, Coordinate end, Coordinate sourcePoint) {
        double A = start.y - end.y;
        double B = end.x - start.x;
        double C = ((start.x * end.y) - (end.x * start.y));
        double nX = 0;
        double nY = 0;
        var v = reverseProblem(start, end);
        if (lessThan(v, BigDecimal.ZERO)) {
            v = v.add(toBigDecimal(360.0));
        }
        if (lessThan(v,toBigDecimal(180.0))) {
            nX = sourcePoint.x + A;
            nY = sourcePoint.y + B;
        } else if (lessThan(v, toBigDecimal(270.0))) {
            nX = sourcePoint.x - A;
            nY = sourcePoint.y - B;
        }
        var v1 = reverseProblem(sourcePoint, new Coordinate(nX, nY));
        return BigDecimalUtils.greaterThan(v1, BigDecimal.ZERO) ? v1 : v1.add(toBigDecimal(360.0));
    }
}
