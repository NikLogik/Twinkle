package ru.nachos.core.fire;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nachos.core.Id;
import ru.nachos.core.config.ConfigUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.fire.lib.FireFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FireFactoryImplTest {

    private Map<Id<Agent>, Agent> agents = new HashMap<>();
    private Fire fire;
    private int distance, perimeter;
    private double mainDirection;

    @Before
    public void init(){
        this.fire = FireUtils.createFire(ConfigUtils.createConfig());
        distance = 12;
        perimeter = 48;
        mainDirection = 135.25;
    }

    @Test
    public void generateFireFrontTest(){
        int amountTest = perimeter / (distance / 2);
        Map<Id<Agent>, Agent> idAgentMap = fire.getFactory().generateFireFront(distance, perimeter);
        Assert.assertEquals(amountTest, idAgentMap.size());
    }

    @Test
    public void generateFireFrontWithOnlyOneHeadAgent(){
        Map<Id<Agent>, Agent> idAgentMap = fire.getFactory().generateFireFront(distance, perimeter);
        List<Agent> collect = idAgentMap.values().stream().filter(Agent::isHead).collect(Collectors.toList());
        Assert.assertEquals(collect.size(), 1);
    }

    @Test
    public void setAgentsToStartPositionTest(){
        FireFactory factory = fire.getFactory();
        Map<Id<Agent>, Agent> idAgentMap = factory.generateFireFront(distance, perimeter);
        Map<Id<Agent>, Agent> twinkles = fire.getTwinkles();
        twinkles.putAll(idAgentMap);
        factory.setAgentToStartPosition(fire, mainDirection);
        Assert.assertEquals(fire.getTwinkles().values().stream().map(Agent::getCoordinate).collect(Collectors.toList()).size(), fire.getTwinkles().size(), 0);
    }
}
