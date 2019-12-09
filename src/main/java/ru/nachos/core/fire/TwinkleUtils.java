package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.utils.GeodeticCalculator;

public final class TwinkleUtils {

    public static void setDistance(Agent twinkle, double distance) {
        twinkle.setDistanceFromStart(distance);
    }

    public static void setCoord(Agent twinkle, Coordinate coord){
        twinkle.setCoordinate(coord);
    }

    public static AgentState getStateByIter(Twinkle twinkle, int iter){
        AgentState plan = null;
        for (AgentState plan1 : twinkle.getStates().values()){
            if (plan1.getIterNum()==iter)
                plan = plan1;
        }
        return plan;
    }

    public static void changeSpeed(Agent agent, double speed){
        agent.setSpeed(speed);
    }



    public static void calculateMiddleParameters(Agent first, Agent second, Agent newA){
        double var = second.getDirection() < first.getDirection() ? second.getDirection() + 360.00 : second.getDirection();
        double direction = ((var - first.getDirection()) / 2) + first.getDirection();
        double speed = (first.getSpeed() + second.getSpeed()) / 2;
        Coordinate position = GeodeticCalculator.middleCoordinate(first.getCoordinate(), second.getCoordinate());
        double distanceFromStart = GeodeticCalculator.median(first.getDistanceFromStart(), second.getDistanceFromStart(),
                first.getCoordinate().distance(second.getCoordinate()));
        newA.setDirection(direction);
        newA.setCoordinate(position);
        newA.setSpeed(speed);
        newA.setDistanceFromStart(distanceFromStart);
        newA.setHead(false);
    }

    /**
     * This method remove neighbour from the right side of target agent
     * and set on this side new neighbour from right side of removed agent
     * @param source - target agent
     * @param right - removing agent
     */
    public static void removeRightNeighbour(Agent right, Agent source){
        Agent x = right.getRightNeighbour();
        source.setRightNeighbour(x);
        x.setLeftNeighbour(source);
        right.setLeftNeighbour(null);
        right.setRightNeighbour(null);
    }

    /**
     * This method remove neighbour from the left side of target agent
     * and set on this side new neighbour from left side of removed agent
     * @param source - target agent
     * @param left - removing agent
     */
    public static void removeLeftNeighbour(Agent left, Agent source){
        Agent x = left.getLeftNeighbour();
        source.setLeftNeighbour(x);
        x.setRightNeighbour(source);
        left.setRightNeighbour(null);
        left.setLeftNeighbour(null);
    }

    /**
     * This method remove target agent from fire front and connect its right
     * and left neighbours between themselves
     * @param twinkle - target agent
     */
    public static void removeTargetAgent(Twinkle twinkle){
        twinkle.getRightNeighbour().setRightNeighbour(twinkle.getLeftNeighbour());
        twinkle.getLeftNeighbour().setRightNeighbour(twinkle.getRightNeighbour());
        twinkle.setRightNeighbour(null);
        twinkle.setLeftNeighbour(null);
    }

}
