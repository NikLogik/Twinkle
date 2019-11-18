package ru.nachos.core.fire;

import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.fire.lib.FireFactory;

import java.util.Map;
import java.util.TreeMap;

class FireFactoryImpl implements FireFactory {

    @Override
    public Agent createTwinkle(Id<Agent> id) {
        return new Twinkle(id);
    }

    @Override
    public AgentState createState() {
        return new TwinkleState();
    }

    @Override
    public AgentState createState(Agent agent) { return new TwinkleState(agent); }

    @Override
    public Map<Id<Agent>, Agent> generateFireFront(int distance, int frontLength){
        int agentAmount = frontLength / (distance / 2);
        TreeMap<Id<Agent>, Agent> map = new TreeMap<>();
        Agent head = null;
        Agent prev = null;
        for (int i=0; i<agentAmount; i++){
            Agent twinkle = createTwinkle(Id.create(i + Fire.Definitions.POST_FIX, Agent.class));
            if (i==0){
                twinkle.setHead(true);
                head = twinkle;
            } else {
                if (i == agentAmount - 1) {
                    twinkle.setRightNeighbour(map.get(head.getId()));
                    head.setLeftNeighbour(twinkle);
                }
                twinkle.setLeftNeighbour(prev);
                prev.setRightNeighbour(twinkle);
            }
            prev = twinkle;
            map.put(twinkle.getId(), twinkle);
        }
        return map;
    }
}
