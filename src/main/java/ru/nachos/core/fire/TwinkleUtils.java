package ru.nachos.core.fire;

import ru.nachos.core.Coord;
import ru.nachos.core.fire.lib.AgentPlan;

import java.text.DecimalFormat;

public final class TwinkleUtils {

    public static void setCoord(Twinkle twinkle, Coord coord){
        twinkle.setCoord(coord);
    }

    public static AgentPlan getPlanByIter(Twinkle twinkle, int iter){
        AgentPlan plan = null;
        for (AgentPlan plan1 : twinkle.getPlans()){
            if (plan1.getItersNumber()==iter)
                plan = plan1;
        }
        return plan;
    }

    /**
     * This method set new neighbour on the right side for target agent
     * @param target - current agent
     * @param newNeighbour - new added agent
     */
    public static void setNewRightNeighbour(Twinkle target, Twinkle newNeighbour){
        Twinkle temp = target.getRightNeighbour();
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
        Twinkle temp = target.getLeftNeighbour();
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
        Twinkle temp = twinkle.getRightNeighbour();
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
        Twinkle temp = twinkle.getLeftNeighbour();
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
    public static Coord calculateMiddleCoords(Coord left, Coord right) {
        double midX = (left.getX() + right.getX()) / 2;
        double midY = (left.getY() + right.getY()) / 2;
        return new Coord(midX, midY);
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
