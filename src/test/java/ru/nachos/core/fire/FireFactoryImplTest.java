package ru.nachos.core.fire;

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
    private int distance, perimeter;
    private double mainDirection;

    @Before
    public void init(){
    }

//    @Test
//    public void generateFireFrontTest(){
//        int amountTest = perimeter / (distance / 2);
//        Map<Id<Agent>, Agent> idAgentMap = fire.getFactory().generateFireFront(fire.getCenterPoint(), distance, perimeter, mainDirection);
//        Assert.assertEquals(amountTest, idAgentMap.size());
//    }
//
//    @Test
//    public void generateFireFrontWithOnlyOneHeadAgent(){
//        Map<Id<Agent>, Agent> idAgentMap = fire.getFactory().generateFireFront(fire.getCenterPoint(), distance, perimeter, mainDirection);
//        List<Agent> collect = idAgentMap.values().stream().filter(Agent::isHead).collect(Collectors.toList());
//        Assert.assertEquals(collect.size(), 1);
//    }

    @Test
    public void convertWindDirection(){
        double a1 = 36;
        double a2 = 78;
        double a3 = 110;
        double a4 = 293;
        double v = convertWindDirection(a1);
        double v1 = convertWindDirection(a2);
        double v2 = convertWindDirection(a3);
        double v3 = convertWindDirection(a4);
    }

    private double convertWindDirection(double startDirection) {
        double var = startDirection;
        if (var < 0.000){
            var += 360.000;
        }
        return var;
    }
}
