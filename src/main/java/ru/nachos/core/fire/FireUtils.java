package ru.nachos.core.fire;

import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.exceptions.FireLeaderException;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class FireUtils {

//    private static Logger logger = Logger.getLogger(FireUtils.class);

    private FireUtils(){}

    public static Fire createFire(){
        return new FireImpl();
    }

    public static Fire createFire(Config config){
        if (config != null) {
            return new FireImpl(config);
        } else {
            return createFire();
        }
    }

    public static void setHeadDirectionOfSpread(Fire fire, double directionOfSpread){ ((FireImpl)fire).setHeadDirection(directionOfSpread);}
    /**
     * This method add new agent in the fire front near by target agent.
     * @param twinkles  - list of fire agents
     * @param target    - target agent
     * @param newTwinkle- new agent which will be add
     * @param side      - if true, agent will be added on the right side from target agent, otherwise on the left side
     */
    public static void setNewTwinkleToFireFront(Map<Id<Agent>, Agent> twinkles, Twinkle target, Twinkle newTwinkle, boolean side){
        if (side){
            target.setRightNeighbour(newTwinkle);
        } else {
            target.setLeftNeighbour(newTwinkle);
        }
        twinkles.put(newTwinkle.getId(), newTwinkle);
    }


    public static Agent getHeadAgent(Map<Id<Agent>, Agent> agents){
        List<Agent> agentList = agents.values().stream().filter(Agent::isHead).collect(Collectors.toList());
        try {
            if (agentList.size() > 1){
                throw new FireLeaderException(FireLeaderException.Code.TOO_MANY);
            }
            if (agentList.size() < 1){
                throw new FireLeaderException(FireLeaderException.Code.TOO_MANY);
            }
        } catch (IllegalArgumentException ex){
//            logger.warn("Agent map have " + agentList.size() + " agents. It will be forcibly fixed.");
        } finally {
            if (agentList.size() > 1){
                for (int i=0; i < agentList.size(); i++){
                    if (i==0){
                        continue;
                    } else {
                        agentList.get(i).setHead(false);
                    }
                }
            } else if (agentList.size() < 1){
                Iterator<Agent> iterator = agents.values().iterator();
                Agent next = iterator.next();
                next.setHead(true);
                agentList.add(next);
            }
            return agentList.get(0);
        }
    }
}
