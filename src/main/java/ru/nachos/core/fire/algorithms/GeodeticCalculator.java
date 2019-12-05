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

    public static boolean checkIntersection(Coordinate c1, Coordinate c2, Coordinate c3, Coordinate c4) {
        //сначала расставим точки по порядку, т.е. чтобы было c1.x <= c2.x
        if (c2.x < c1.x) {
            Coordinate tmp = c1;
            c1 = c2;
            c2 = tmp;
        }
        //и c3.x <= c4.x
        if (c4.x < c3.x) {
            Coordinate tmp = c3;
            c3 = c4;
            c4 = tmp;
        }
        //проверим существование потенциального интервала для точки пересечения отрезков
        if (c2.x < c3.x) {
            return false; //ибо у отрезков нету взаимной абсциссы
        }
        //если оба отрезка вертикальные
        if((c1.x - c2.x == 0) && (c3.x - c4.x == 0)) {
            //если они лежат на одном X
            if(c1.x == c3.x) {
                //проверим пересекаются ли они, т.е. есть ли у них общий Y
                //для этого возьмём отрицание от случая, когда они НЕ пересекаются
                if (!((Math.max(c1.y, c2.y) < Math.min(c3.y, c4.y)) ||
                        (Math.min(c1.y, c2.y) > Math.max(c3.y, c4.y)))) {
                    return true;
                }
            }
            return false;
        }
        //найдём коэффициенты уравнений, содержащих отрезки
        //f1(x) = A1*x + b1 = y
        //f2(x) = A2*x + b2 = y

        //если первый отрезок вертикальный
        if ((c1.x - c2.x) == 0.00) {
            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = c1.x;
            double A2 = (c3.y - c4.y) / (c3.x - c4.x);
            double b2 = c3.y - A2 * c3.x;
            double Ya = A2 * Xa + b2;
            if (c3.x <= Xa && c4.x >= Xa && Math.min(c1.y, c2.y) <= Ya &&
                    Math.max(c1.y, c2.y) >= Ya) {
                return true;
            }
            return false;
        }
        //если второй отрезок вертикальный
        if ((c3.x - c4.x) == 0.00) {
            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = c3.x;
            double A1 = (c1.y - c2.y) / (c1.x - c2.x);
            double b1 = c1.y - A1 * c1.x;
            double Ya = A1 * Xa + b1;
            if (c1.x <= Xa && c2.x >= Xa && Math.min(c3.y, c4.y) <= Ya &&
                    Math.max(c3.y, c4.y) >= Ya) {
                return true;
            }
            return false;
        }
        //оба отрезка невертикальные
        double A1 = (c1.y - c2.y) / (c1.x - c2.x);
        double A2 = (c3.y - c4.y) / (c3.x - c4.x);
        double b1 = c1.y - A1 * c1.x;
        double b2 = c3.y - A2 * c3.x;
        if (A1 == A2) {
            return false; //отрезки параллельны
        }
        //Xa - абсцисса точки пересечения двух прямых
        double Xa = (b2 - b1) / (A1 - A2);
        if ((Xa < Math.max(c1.x, c3.x)) || (Xa > Math.min( c2.x, c4.x))) {
            return false; //точка Xa находится вне пересечения проекций отрезков на ось X
        }
        else {
            return true;
        }
    }
}
