package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;

import java.text.DecimalFormat;

public final class TwinkleUtils {

    public static void setDistance(Agent twinkle, double distance) {
        twinkle.setDistanceFromStart(distance);
    }

    public static void setCoord(Agent twinkle, Coordinate coord){
        twinkle.setPoint(coord);
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

    /**
     * This method set new neighbour on the right side for target agent
     * @param target - current agent
     * @param newNeighbour - new added agent
     */
    public static void setNewRightNeighbour(Twinkle target, Twinkle newNeighbour){
        Agent temp = target.getRightNeighbour();
        target.setRightNeighbour(newNeighbour);
        temp.setLeftNeighbour(newNeighbour);
        newNeighbour.setLeftNeighbour(target);
        newNeighbour.setRightNeighbour(temp);
    }

    /**
     * This method set new neighbour on the left side for target agent
     * @param target - current agent
     * @param newNeighbour - new added agent
     */
    public static void setNewLeftNeighbour(Twinkle target, Twinkle newNeighbour){
        Agent temp = target.getLeftNeighbour();
        target.setLeftNeighbour(newNeighbour);
        temp.setRightNeighbour(newNeighbour);
        newNeighbour.setRightNeighbour(target);
        newNeighbour.setLeftNeighbour(temp);
    }

    /**
     * This method remove neighbour from the right side of target agent
     * and set on this side new neighbour from right side of removed agent
     * @param twinkle - target agent
     */
    public static void removeRightNeighbour(Twinkle twinkle){
        Agent temp = twinkle.getRightNeighbour();
        twinkle.setRightNeighbour(temp.getRightNeighbour());
        temp.getRightNeighbour().setLeftNeighbour(twinkle);
        temp.setLeftNeighbour(null);
        temp.setRightNeighbour(null);
    }

    /**
     * This method remove neighbour from the left side of target agent
     * and set on this side new neighbour from left side of removed agent
     * @param twinkle - target agent
     */
    public static void removeLeftNeighbour(Twinkle twinkle){
        Agent temp = twinkle.getLeftNeighbour();
        twinkle.setLeftNeighbour(temp.getLeftNeighbour());
        temp.getLeftNeighbour().setRightNeighbour(twinkle);
        temp.setLeftNeighbour(null);
        temp.setRightNeighbour(null);
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

    /**
     * This method calculate coordinates (X, Y) of center line segment between two source points
     * @param left  - coordinates left point
     * @param right - coorfinates right point
     * @return point with middle coordinates
     */
    public static Coordinate calculateMiddleCoords(Coordinate left, Coordinate right) {
        double midX = (left.x + right.x) / 2;
        double midY = (left.y + right.y) / 2;
        return new Coordinate(midX, midY);
    }

    /**
     * This method calculate velocity`s vector, which has direction
     * as middle value between two source directions. Use degrees units
     * @param left  - direction of the left vector
     * @param right - direction of the right vector
     * @return  middle value of the two vector`s directions
     */
    public static double calculateMiddleDirection(double left, double right){
        DecimalFormat format = new DecimalFormat("#.###");
        double result = (left + right) / 2;
        result = Double.parseDouble(format.format(result));
        return result;
    }
}
