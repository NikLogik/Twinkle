package ru.nachos.core.fire;

import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;

import java.util.Map;

public final class FireUtils {

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

    /**
     * This method add new agent in the fire front on the right side of the target agent by default.
     * @param twinkles  - list of fire agents
     * @param target    - target agent
     * @param newTwinkle- new agent which to be add
     */
    public static void setNewTwinkleToFireFront(Map<Id<Agent>, Agent> twinkles, Twinkle target, Twinkle newTwinkle){
        setNewTwinkleToFireFront(twinkles, target, newTwinkle, true);
    }

    /**
     * This method add new agent in the fire front near by target agent.
     * @param twinkles  - list of fire agents
     * @param target    - target agent
     * @param newTwinkle- new agent which will be add
     * @param side      - if true, agent will be added on the right side from target agent, otherwise on the left side
     */
    public static void setNewTwinkleToFireFront(Map<Id<Agent>, Agent> twinkles, Twinkle target, Twinkle newTwinkle, boolean side){
        if (side){
            TwinkleUtils.setNewRightNeighbour(target, newTwinkle);
        } else {
            TwinkleUtils.setNewLeftNeighbour(target, newTwinkle);
        }
        twinkles.put(newTwinkle.getId(), newTwinkle);
    }
}
