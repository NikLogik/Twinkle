package git.niklogik.core.fire;

import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.FireFactory;
import git.niklogik.core.utils.GeodeticCalculator;
import org.apache.commons.math.util.MathUtils;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;
import static git.niklogik.core.utils.GeodeticCalculator.directProblem;

public class FireFactoryImpl implements FireFactory {

    @Override
    public Agent createTwinkle(String agentPrefixId) {
        return new Twinkle(UUID.randomUUID());
    }

    public Agent createTwinkle(double direction) {
        Twinkle twinkle = new Twinkle(UUID.randomUUID());
        twinkle.setDirection(toBigDecimal(direction));
        return twinkle;
    }

    /**
     * @param center    - координаты центра пожара
     * @param distance  - расстояние между агентами
     * @param perimeter - длина фронта пожара
     * @param direction - направление ветра
     * @return
     */
    @Override
    public Map<UUID, Agent> generateFireFront(Coordinate center, int distance, int perimeter, double direction) {
        TreeMap<UUID, Agent> map = new TreeMap<>();
        int agentAmount = perimeter / (distance / 2);
        var radius = calculateRadius(perimeter);
        double angleIncrement = MathUtils.round(360.0 / agentAmount, 2);
        Agent head = null;
        Agent prev = null;
        for (int i = 0; i < agentAmount; i++, direction += angleIncrement) {
            double tempDirection = direction > 360.0 ? direction - 360.000 : direction;
            Coordinate position = directProblem(center, radius, toBigDecimal(tempDirection));
            Agent twinkle = createTwinkle(tempDirection);
            twinkle.setCoordinate(position);
            twinkle.setDistanceFromStart(radius.doubleValue());
            if (i == 0) {
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

    private BigDecimal calculateRadius(int perimeter) {
        return toBigDecimal(MathUtils.round(perimeter / (2 * Math.PI), 2));
    }
}
