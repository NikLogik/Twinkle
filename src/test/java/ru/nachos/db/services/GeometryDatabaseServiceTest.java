package ru.nachos.db.services;

import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.Network;
import ru.nachos.db.repository.osm.PolygonOsmModelRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeometryDatabaseServiceTest {

    @Autowired
    private PolygonOsmModelRepository polygonOsmModelRepository;
    @Autowired
    private GeometryDatabaseService geometryDatabaseService;
    private Coordinate[] coordinates;
    private Network network;

    @Before
    public void init(){
        this.coordinates = new Coordinate[]{
                new Coordinate(3757497.46, 5622298.79),
                new Coordinate(3757415.08, 5573034.79),
                new Coordinate(3839656.31, 5617201.27),
                new Coordinate(3837952.75, 5700096.08),
                new Coordinate(3757497.46, 5622298.79)
        };
        this.network = NetworkUtils.createNetwork();
    }

    @Test
    public void createNetwork(){
        Network networkFromBoundaryBox = geometryDatabaseService.createNetworkFromBoundaryBox(network, coordinates);
        Assert.assertTrue(!networkFromBoundaryBox.getPolygones().isEmpty());
    }
}