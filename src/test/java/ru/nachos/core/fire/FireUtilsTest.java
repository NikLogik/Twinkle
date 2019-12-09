package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.utils.GeodeticCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireUtilsTest {

    Map<Id<Agent>, Agent> map = new HashMap<>();

    @Before
    public void init() {
        map.put(Id.createAgentId(1), new Twinkle(Id.createAgentId(1)));
        map.put(Id.createAgentId(2), new Twinkle(Id.createAgentId(2)));
        map.put(Id.createAgentId(3), new Twinkle(Id.createAgentId(3)));
        map.put(Id.createAgentId(4), new Twinkle(Id.createAgentId(4)));
        map.put(Id.createAgentId(5), new Twinkle(Id.createAgentId(5)));
        map.put(Id.createAgentId(6), new Twinkle(Id.createAgentId(6)));
    }

    @Test
    public void calculateCoordIncrementTest() {
        Coordinate coordinate = GeodeticCalculator.directTask(new Coordinate(5559711.64, 3736820.58), 541, 45.00);
        System.out.println(coordinate.x);
        Assert.assertNotNull(coordinate);
    }

    @Test
    public void getHeadAgentTest() {
        List<Agent> values = new ArrayList<>(map.values());
        Agent agent = values.get(RandomUtils.nextInt(0, map.size()));
        agent.setHead(true);
        Agent headAgent = FireUtils.getHeadAgent(map);
        Assert.assertEquals(headAgent, agent);
    }
}