package git.niklogik.core.fire;

import org.apache.commons.math.util.MathUtils;
import org.locationtech.jts.geom.Coordinate;
import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.fire.lib.FireFactory;
import git.niklogik.core.utils.GeodeticCalculator;

import java.util.Map;
import java.util.TreeMap;

public class FireFactoryImpl implements FireFactory {

    @Override
    public Agent createTwinkle(Id<Agent> id) {
        return new Twinkle(id);
    }

    public Agent createTwinkle(Id<Agent> id, double direction){
        Twinkle twinkle = new Twinkle(id);
        twinkle.setDirection(direction);
        return twinkle;
    }
    /**
     *
     * @param center - координаты центра пожара
     * @param distance - расстояние между агентами
     * @param perimeter - длина фронта пожара
     * @param direction - направление ветра
     * @return
     */
    @Override
    public Map<Id<Agent>, Agent> generateFireFront(Coordinate center, int distance, int perimeter, double direction){
        TreeMap<Id<Agent>, Agent> map = new TreeMap<>();
        int agentAmount = perimeter / (distance / 2);
        double radius = calculateRadius(perimeter);
        double angleIncrement = MathUtils.round(360.0 / agentAmount, 2);
        Agent head = null;
        Agent prev = null;
        for (int i=0; i<agentAmount; i++, direction += angleIncrement){
            double tempDirection = direction > 360.0 ? direction - 360.000 : direction;
            Coordinate position = GeodeticCalculator.directProblem(center, radius, tempDirection);
            Agent twinkle = createTwinkle(Id.create(i + Fire.Definitions.POST_FIX, Agent.class), tempDirection);
            twinkle.setCoordinate(position);
            twinkle.setDistanceFromStart(radius);
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

    private double calculateRadius(int perimeter){ return MathUtils.round(perimeter / (2 * Math.PI), 2); }
}
