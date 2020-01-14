package ru.nachos.db.repository.fire;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.db.model.fire.ContourLine;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContourLineRepositoryTest {

    @Autowired
    ContourLineRepository lineRepository;

    @Test
    public void testIntersections(){
        GeometryFactory factory = new GeometryFactory();
        LineString lineString = factory.createLineString(new Coordinate[]{new Coordinate(3756270.4, 5545715.9), new Coordinate(3756580.3, 5544885.7)});
        lineString.setSRID(3857);
        List<ContourLine> i = lineRepository.findContourLineByHorizontal(lineString);
        List<Geometry> intersections = new ArrayList<>();
        long l = System.currentTimeMillis();
        for (ContourLine line : i){
            Geometry intersection = lineString.intersection(line.getHorizontal());
            intersections.add(intersection);
        }
        System.out.println(System.currentTimeMillis() - l);
        intersections.clear();
        l = System.currentTimeMillis();
        for (ContourLine line : i){
            Geometry intersectionsByGeometry = lineRepository.findIntersectionsByGeometry(lineString, line.getHorizontal());
            intersections.add(intersectionsByGeometry);
        }
        System.out.println(System.currentTimeMillis() - l);
        Assert.assertEquals(intersections.size(), i.size());
    }
}