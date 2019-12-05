package ru.nachos.core.fire.algorithms;

import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Assert;
import org.junit.Test;

public class GeodeticCalculatorTest {

    @Test
    public void directTaskTest(){
        Coordinate startPoint = new Coordinate(3736820.58, 5559711.64);
        Coordinate c1 = GeodeticCalculator.directTask(startPoint, 25, 289.00); // СЗ румб +x,-y
        Coordinate c2 = GeodeticCalculator.directTask(startPoint, 45, 23.031394); //СВ румб +x,+y
        Coordinate c3 = GeodeticCalculator.directTask(startPoint, 32, 110.1); //ЮВ румб -x,+y
        Coordinate c4 = GeodeticCalculator.directTask(startPoint, 12, 210.00); //ЮЗ румб -x,-y
        Assert.assertTrue(c1.x > startPoint.x && c1.y < startPoint.y);
        Assert.assertTrue(c2.x > startPoint.x && c2.y > startPoint.y);
        Assert.assertTrue(c3.x < startPoint.x && c3.y > startPoint.y);
        Assert.assertTrue(c4.x < startPoint.x && c4.y < startPoint.y);
    }

}