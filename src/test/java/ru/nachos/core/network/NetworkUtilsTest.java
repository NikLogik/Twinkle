package ru.nachos.core.network;

import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nachos.core.Id;
import ru.nachos.core.controller.ControllerUtils;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtilsTest {

    private Network network;
    private PolygonV2 polygonV2;

    @Before
    public void init() {
        ControllerUtils.createController();
        network = NetworkUtils.createNetwork();
        Id<PolygonV2> id = Id.createPolygonId(123456);
        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(-2.0, -2.0));
        list.add(new Coordinate(-2.0, 2.0));
        list.add(new Coordinate(2.0, 2.0));
        list.add(new Coordinate(2.0, -2.0));
        list.add(new Coordinate(2.5, -0.5));
        list.add(new Coordinate(-2.0, -2.0));
        polygonV2 = network.getFactory().createPolygon(id, list);
    }

    @Test
    public void isPointInsidePolygonTest() {
        boolean insidePolygon = NetworkUtils.isInsidePolygon(polygonV2, network.getFactory().getGeomFactory().createPoint(new Coordinate(1.0, 1.0)));
        Assert.assertTrue(insidePolygon);
    }

    @Test
    public void calculateBoundaryBoxTest(){
        Coordinate testCoordinate = new Coordinate(654781.565478, 547845.123456);
        double testDistance = 1560.60;
        Coordinate[] assertCoords = new Coordinate[]{
                new Coordinate(testCoordinate.x + testDistance, testCoordinate.y),
                new Coordinate(testCoordinate.x, testCoordinate.y - testDistance),
                new Coordinate(testCoordinate.x - testDistance, testCoordinate.y),
                new Coordinate(testCoordinate.x, testCoordinate.y + testDistance)
        };
        Coordinate[] coordinates = NetworkUtils.calculateBoundaryBox(testCoordinate, testDistance);
        Assert.assertArrayEquals(assertCoords, coordinates);
    }


    @Test
    public void findCrossPointWithPolygonTest(){
        Coordinate coord1 = new Coordinate(-1.0, -3.0);
        Coordinate coord2 = new Coordinate(-1.5, 1.0);
        Coordinate polygon = NetworkUtils.findCrossPointWithPolygon(coord1, coord2, polygonV2);
        boolean yes = polygon.x!=0.0 && polygon.y!=0.0;
        System.out.println(polygon);
        Assert.assertTrue(yes);
    }


}