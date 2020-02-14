package ru.nachos.db.repository.osm;

import com.vividsolutions.jts.geom.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.db.entities.osm.PolygonOsmModel;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PolygonOsmModelRepositoryTest {
    @Autowired
    private PolygonOsmModelRepository repository;

    @Test
    public void findAllPolygonsByNature(){
        String nature = "beach";
        List<PolygonOsmModel> polygonOsmModelsByNatural = repository.findPolygonOsmModelsByNatural(nature);
        Assert.assertTrue(!polygonOsmModelsByNatural.isEmpty());
    }

    @Test
    public void findPolygonsFromWay(){
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(3757497.46, 5622298.79),
                new Coordinate(3757415.08, 5573034.79),
                new Coordinate(3839656.31, 5617201.27),
                new Coordinate(3837952.75, 5700096.08),
                new Coordinate(3757497.46, 5622298.79)
        };
        GeometryFactory factory = new GeometryFactory();
        Polygon polygon = factory.createPolygon(new Coordinate[]{coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[0]});
        polygon.setSRID(3857);
        List<PolygonOsmModel> polygonOsmModelsByWayContains = repository.findAllPolygonOsmModelsInsideWay(polygon);
        Assert.assertTrue(!polygonOsmModelsByWayContains.isEmpty());
    }

    @Test
    public void findSRID(){
        int srid = repository.getSRID();
        Assert.assertTrue(srid == 3857);
    }
}