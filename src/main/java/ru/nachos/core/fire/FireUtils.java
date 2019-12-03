package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     *
     * @param firstPoint - point with coordinates the previous position
     * @param length - length from previous position ({@param firstPoint}
     * @param direction - direction where agent are moved. Calculated from y-positive axis
     * @return new {@link Coordinate} agents position
     */
    public static Coordinate calculateCoordIncrement(Coordinate firstPoint, double length, double direction){
        if (direction > 360.000) {
            direction -= 360.000;
        }
        double radians;
        double dX = 0.0;
        double dY = 0.0;
        if (direction <= 90.000) {
            radians = direction * Math.PI/180;
            dX = firstPoint.x + Math.cos(radians) * length;
            dY = firstPoint.y + Math.sin(radians) * length;
        } else if (direction > 90.000 && direction <= 180.000){
            radians = (180 - direction) * Math.PI/180;
            dX = firstPoint.x - Math.cos(radians) * length;
            dY = firstPoint.y + Math.sin(radians) * length;
        } else if (direction > 180.000 && direction <= 270.000){
            radians = (direction - 180.000) * Math.PI/180;
            dX = firstPoint.x - Math.cos(radians) * length;
            dY = firstPoint.y - Math.sin(radians) * length;
        } else if (direction > 270.000){
            radians = (360.000 - direction) * Math.PI/180;
            dX = firstPoint.x + Math.cos(radians) * length;
            dY = firstPoint.y - Math.sin(radians) * length;
        }
        return new Coordinate(dX, dY);
    }

    public static Agent getHeadAgent(Map<Id<Agent>, Agent> agents){
        List<Agent> agentList = agents.values().stream().filter(Agent::isHead).collect(Collectors.toList());
        if (agentList.size() != 1){
            throw new IllegalArgumentException("Fire front have to contains only 1 head agent");
        }
        return agentList.get(0);
    }
}
