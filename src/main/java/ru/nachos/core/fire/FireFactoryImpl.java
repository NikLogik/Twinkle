package ru.nachos.core.fire;

import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentPlan;
import ru.nachos.core.fire.lib.FireFactory;

public class FireFactoryImpl implements FireFactory {

    @Override
    public Agent createTwinkle(Id<Agent> id) {
        return new Twinkle(id);
    }

    @Override
    public AgentPlan createPlan() {
        return new TwinklePlan();
    }
}
