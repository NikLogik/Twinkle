package ru.nachos.core.network;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.TwinkleApplication;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.OsmDatabaseManager;
import ru.nachos.db.repository.PolygonRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TwinkleApplication.class)
public class NetworkUtilsTest {

    private Network network;
    private PolygonV2 polygonV2;
    private Coordinate[] coordinates;
    @Autowired
    OsmDatabaseManager manager;
    @Autowired
    PolygonRepository repository;

    @Before
    public void init() {
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

        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .username("model")
                .password("Bc9Q_13k$h![lJx7oW1vc")
                .url("jdbc:postgresql://185.124.188.247:5432/model")
                .build();
        manager.setDataSource(dataSource);

        coordinates = new Coordinate[]{
                new Coordinate(3757497.46, 5622298.79),
                new Coordinate(3757415.08, 5573034.79),
                new Coordinate(3839656.31, 5617201.27),
                new Coordinate(3837952.75, 5700096.08),
                new Coordinate(3757497.46, 5622298.79)
        };
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
    public void createNetworkTest(){
        NetworkFactory factory = network.getFactory();
        Polygon polygon = factory.getGeomFactory().createPolygon(new Coordinate[]{coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[0]});
        List<PolygonV2> polygonsFromBoundaryBox = repository.getPolygonsFromBoundaryBox(factory, polygon);
        WKTWriter writer = new WKTWriter();
        List<String> pol = new LinkedList<>();
        for (PolygonV2 p : polygonsFromBoundaryBox){
            pol.add(writer.write(p));
        }
        polygonsFromBoundaryBox.forEach(poly-> System.out.println(poly.getId() + ", " + poly.getPolygonType()));
        Map<PolygonType, List<PolygonV2>> collect = polygonsFromBoundaryBox.stream().collect(Collectors.groupingBy(PolygonV2::getPolygonType, Collectors.toList()));
        Assert.assertTrue(collect.size() != 0);
    }

    @Test
    public void findCrossPointWithPolygonTest(){
        Coordinate coord1 = new Coordinate(-1.0, -3.0);
        Coordinate coord2 = new Coordinate(-1.5, 1.0);
        Coordinate[] polygon = GeodeticCalculator.findCrossPointWithPolygon(coord1, coord2, polygonV2);
        System.out.println(polygon[0].toString() + polygon[1].toString() + polygon[2].toString());
        Assert.assertTrue(polygon[0] != null);
        Assert.assertTrue(polygon[1] != null);
        Assert.assertTrue(polygon[2] != null);
    }
}