package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
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

    public static void changeId(Agent twinkle, Id<Agent> newId){
        ((Twinkle) twinkle).setId(newId);
    }

    public static void changeSpeed(Agent agent, double speed){
        agent.setSpeed(speed);
    }

    public static void createMiddleAgent(Agent left, Agent right, Agent newAgent){
        left.setRightNeighbour(newAgent);
        right.setLeftNeighbour(newAgent);
        newAgent.setLeftNeighbour(left);
        newAgent.setRightNeighbour(right);
        double var = right.getDirection() < left.getDirection() ? right.getDirection() + 360.00 : right.getDirection();
        double direction = ((var - left.getDirection()) / 2) + left.getDirection();
        double speed = (left.getSpeed() + right.getSpeed()) / 2;
        Coordinate position = GeodeticCalculator.middleCoordinate(left.getCoordinate(), right.getCoordinate());
        double distanceFromStart = GeodeticCalculator.median(left.getDistanceFromStart(), right.getDistanceFromStart(),
                left.getCoordinate().distance(right.getCoordinate()));
        newAgent.setDirection(direction);
        newAgent.setCoordinate(position);
        newAgent.setSpeed(speed);
        newAgent.setDistanceFromStart(distanceFromStart);
        newAgent.setHead(false);
    }

    public static void setAgentBetween(Agent left, Agent right, Agent target){
        target.setLeftNeighbour(left);
        target.setRightNeighbour(right);
        left.setRightNeighbour(target);
        right.setLeftNeighbour(target);
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
