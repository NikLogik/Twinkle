package ru.nachos.core.fire;

import ru.nachos.core.Coord;
import ru.nachos.core.Id;
import ru.nachos.core.controller.ControllerImpl;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.fire.lib.FireFactory;

import java.util.Map;

public final class FireUtils {

    private FireUtils(){}

    private static final FireFactory factory = ControllerImpl.getInstance().getFire().getFactory();

    public static Fire createFire(){
        return new FireImpl(new FireFactoryImpl());
    }

    public static FireFactory getFactory(){
        return factory;
    }

    public static final class FireBuilder{

        private FireImpl fire;

        public FireBuilder(Fire fire, Coord center, int perimeter, int accuracy){
            this.fire = (FireImpl) fire;
            this.fire.setCenter(center);
            this.fire.setPerimeter(perimeter);
            this.fire.setAccuracy(accuracy);
        }

        public FireBuilder setName(String name){
            this.fire.setName(name);
            return this;
        }

        public FireBuilder setFireSpeed(double speed){
            this.fire.setFireSpeed(speed);
            return this;
        }

        public Fire build(){
            return this.fire;
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
