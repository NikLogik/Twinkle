package git.niklogik.core.fire.algorithms;


import git.niklogik.core.network.lib.Node;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;

import java.math.BigDecimal;

public interface FireSpreadCalculator {

    BigDecimal calculateForDirection(BigDecimal fireSpeed, BigDecimal agentDirection);

    BigDecimal freeFireSpeed(ForestFuelTypeDao forestFuelTypeDao, BigDecimal windSpeed);

    /**
     * This method calculate relief constraint for the speed of fire spread
     * @return value relief coefficient
     */
    BigDecimal reliefCoefficient(Node from, Node to);
}
