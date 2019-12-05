package ru.nachos.core.fire.algorithms;

import com.vividsolutions.jts.geom.Coordinate;

public class GeodeticCalculator {

    /**
     * Решает прямую геодезическую задачу, вычисляя координаты искомой точки по исходной точке, расстоянию до искомой точки и
     * <a href='https://ru.wikipedia.org/wiki/%D0%94%D0%B8%D1%80%D0%B5%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D1%83%D0%B3%D0%BE%D0%BB'>дирекционному углу</a>
     * @param startPoint - координата исходной точки
     * @param length - расстояние от исходной точки до искомой
     * @param direction - дирекционный угол от оси X до трезка между исходной и искомой точками (по часовой стрелке)
     * @return рассчитанная точка
     */
    public static Coordinate directTask(Coordinate startPoint, double length, double direction){
        //угол не может быть больше 360 градусов
        if (direction > 360.000){
            direction -= 360.000;
        }
        //определяем приращение координат
        double angleRadians = Math.toRadians(direction);
        double dX = length * Math.cos(angleRadians);
        double x2 = startPoint.x + dX;
        double dY = length * Math.sin(angleRadians);
        double y2 = startPoint.y + dY;
        return new Coordinate(x2, y2);
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
}
