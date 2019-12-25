package ru.nachos.db.repository.fire;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.db.model.fire.FireFrontModel;
import ru.nachos.db.model.fire.FireModel;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FireFrontModelRepositoryTest {
    @Autowired
    private FireModelRepository repository;
    @Autowired
    private FireFrontModelRepository fireFrontModelRepository;

    private FireModel model;
    private FireFrontModel fireFrontModel;

    @Before
    public void initializeModels() {
        model = new FireModel();
        model.setIterations_num(10);
        GeometryFactory factory = new GeometryFactory();
        Geometry polygon = factory.createPolygon(new Coordinate[]{

                new Coordinate(34.04065, 44.65720),
                new Coordinate(34.04348, 44.65547),
                new Coordinate(34.042165, 44.655820),
                new Coordinate(34.04065, 44.65720)
        });
        polygon.setSRID(4326);
        fireFrontModel = new FireFrontModel();
        fireFrontModel.setIterAmount(model.getIterations_num());
        fireFrontModel.setIterNumber(3);
        fireFrontModel.setPolygon(polygon);
    }

    @Test
    public void shouldCreateNewFireModel(){
        FireModel save = repository.save(model);
        Optional<FireModel> byId = repository.findById(save.getFireId());
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    public void shouldCreateNewFireFront(){
        FireModel byId1 = repository.findAll().get(0);
        fireFrontModel.setFire(byId1);
        FireFrontModel save = fireFrontModelRepository.save(fireFrontModel);
        Optional<FireFrontModel> byId = fireFrontModelRepository.findById(save.getId());
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    public void findFireFrontModelByFireId(){
        FireFrontModel fireFrontModelByFire_idAndIter_number = fireFrontModelRepository.findFireFrontModelByFire_FireIdAndIterNumber(109L, 5);
        Assert.assertNotNull(fireFrontModelByFire_idAndIter_number);
    }

    @Test
    public void deleteFireByFireId(){
        fireFrontModelRepository.deleteAllByFire_FireId(122);
        repository.deleteByFireId(122);
    }
}