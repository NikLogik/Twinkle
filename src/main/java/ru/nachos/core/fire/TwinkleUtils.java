package ru.nachos.core.fire;

import ru.nachos.core.Coord;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentPlan;

public final class TwinkleUtils {

    public static Coord getCoord(Agent agent){
        return agent.getCoord();
    }

    public static void setCoord(Twinkle twinkle, Coord coord){
        twinkle.setCoord(coord);
    }

    public static AgentPlan getPlanByIter(Twinkle twinkle, int iter){
        AgentPlan plan = null;
        for (AgentPlan plan1 : twinkle.getPlans()){
            if (plan1.getItersNumber()==iter)
                plan = plan1;
        }
        return plan;
    }

}
