package ru.nachos.core.fire.lib;

import ru.nachos.core.Id;

import java.util.Map;

public interface FireFactory {

    Agent createTwinkle(Id<Agent> id);

    AgentState createState();

    AgentState createState(Agent agent);

    Map<Id<Agent>, Agent> generateFireFront(int distance, int frontLength);
}
