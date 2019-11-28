package ru.nachos.db;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.TwinkleApplication;

import javax.sql.DataSource;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TwinkleApplication.class)
public class PolygonRepositoryImplTest {

    @Autowired
    PolygonRepositoryImpl repository;
    @Autowired
    OsmDatabaseManager manager;

    Coordinate[] coordinates;

    @Before
    public void init(){
        DataSource dataSourceBuilder = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .username("model")
                .password("Bc9Q_13k$h![lJx7oW1vc")
                .url("jdbc:postgresql://185.124.188.247:5432/model")
                .build();
        manager.setDataSource(dataSourceBuilder);
        coordinates = new Coordinate[]{
                new Coordinate(3757497.46, 5622298.79),
                new Coordinate(3757415.08, 5573034.79),
                new Coordinate(3839656.31, 5617201.27),
                new Coordinate(3837952.75, 5700096.08),
                new Coordinate(3757497.46, 5622298.79)
        };
    }

    @Test
    public void getSRIDTest(){
        int srid = repository.getSRID();
        System.out.println(srid);
        Assert.assertTrue(srid!=0);
    }

    @Test
    public void findPolygonByIdTest(){
        Polygon polygonById = repository.getPolygonById(384039569);
        Assert.assertNotNull(polygonById);
        Assert.assertEquals(Polygon.class, polygonById.getClass());
    }

    @Test
    public void findPolygonFromBoundaryBoxTest(){
        GeometryFactory factory = new GeometryFactory();
        Polygon polygon1 = factory.createPolygon(coordinates);
        Map<String, Polygon> polygons = repository.getPolygonsFromBoundaryBox(polygon1);
        Assert.assertNotNull(polygons);
        Assert.assertTrue(polygons.size() > 0);
    }
}