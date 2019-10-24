package ru.nachos.core.fire.lib;

import ru.nachos.core.Id;

public interface FireFactory {

    Agent createTwinkle(Id<Agent> id);

    AgentPlan createPlan();
}
