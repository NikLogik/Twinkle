package ru.nachos.core.controller;

import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.TwinkleApplication;
import ru.nachos.core.config.ConfigImpl;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.experimental.TwinkleCSVWriter;

import javax.sql.DataSource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TwinkleApplication.class)
public class ControllerImplTest {
    @Autowired
    private InitialPreprocessingData data;
    private Coordinate[] coordinates;
    private DataSource dataSource;

    @Before
    public void init(){
        ConfigImpl config = (ConfigImpl) ConfigUtils.createConfig();
        config.setFireCenterCoordinate(new Coordinate(44.97385, 33.88063));
        data.create(config);
        data.preprocessing();
        coordinates = new Coordinate[]{
                new Coordinate(3757497.46, 5622298.79),
                new Coordinate(3757415.08, 5573034.79),
                new Coordinate(3839656.31, 5617201.27),
                new Coordinate(3837952.75, 5700096.08),
                new Coordinate(3757497.46, 5622298.79)
        };
        NetworkUtils.createNetwork(data.getNetwork(), coordinates);
    }

    @Test
    public void doIterationTest(){
        Controller controller = ControllerUtils.createController(data);
        controller.run();
        Fire fire = controller.getFire();
        InitialPreprocessingData preprocessingData = controller.getPreprocessingData();
        TwinkleCSVWriter writer = null;
        try {
            writer = new TwinkleCSVWriter(fire.getTwinkles());
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.writeAgents();
    }
}