package ru.nachos.core.fire;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;

import java.util.HashMap;
import java.util.Map;

public class FireFactoryImplTest {

    private Map<Id<Agent>, Agent> agents = new HashMap<>();
    private Fire fire;

    @Before
    public void init(){
        this.fire = FireUtils.createFire();
    }

    @Test
    public void generateFireFrontTest(){
        int distance = 10;
        int perimeter = 678;
        int amountTest = perimeter / (distance / 2);
        Map<Id<Agent>, Agent> idAgentMap = fire.getFactory().generateFireFront(distance, perimeter);
        System.out.println(amountTest + ", " + idAgentMap.size());
        Assert.assertEquals(amountTest, idAgentMap.size());
    }
}
