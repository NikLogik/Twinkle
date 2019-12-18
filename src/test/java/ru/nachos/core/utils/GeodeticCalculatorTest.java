package ru.nachos.core.utils;

import com.vividsolutions.jts.geom.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;

public class GeodeticCalculatorTest {

    private static Polygon polygon;
    private static LinkedList<Coordinate> coordinates = new LinkedList<>();

    @BeforeClass
    public static void init(){
        GeometryFactory factory = new GeometryFactory();
        coordinates.add(new Coordinate(34.3096161, 45.0516956));
        coordinates.add(new Coordinate(34.3103027, 45.0172436));
        coordinates.add(new Coordinate(34.3460083, 44.9919980));
        coordinates.add(new Coordinate(34.4043732, 44.9978249));
        coordinates.add(new Coordinate(34.4345856, 45.0298622));
        coordinates.add(new Coordinate(34.4043732, 45.0628516));
        coordinates.add(new Coordinate(34.3738174, 45.0708535));
        coordinates.add(new Coordinate(34.3346786, 45.0684288));
        coordinates.add(new Coordinate(34.3123198, 45.0542726));
        coordinates.add(new Coordinate(34.3260312, 45.0667466));
        coordinates.add(new Coordinate(34.3096161, 45.0515743));
        coordinates.add(new Coordinate(34.3096161, 45.0516956));
        polygon = factory.createPolygon(coordinates.toArray(new Coordinate[coordinates.size()]));
    }

    @Test
    public void distanceTest(){
        LinkedList<Coordinate> list = new LinkedList<>();
        list.add(new Coordinate(2.0, 2.0));
        list.add(new Coordinate(-2.0, -2.0));
        GeometryFactory factory = new GeometryFactory();
        LineString lineString = factory.createLineString(list.toArray(new Coordinate[0]));
        Coordinate vertex = new Coordinate(1.0, -1.0);
        double distance = lineString.distance(factory.createPoint(vertex));
        System.out.println(distance);
        Assert.assertTrue(distance > 0.0);
    }

    @Test
    public void crossingSegmentTest(){
        Assert.assertTrue(!polygon.isSimple());
    }

}