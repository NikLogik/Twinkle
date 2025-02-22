package git.niklogik.core.fire.algorithms;


import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.network.lib.Node;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;

import java.math.BigDecimal;

public interface FireSpreadCalculator {

    void calculateSpeedOfSpreadWithArbitraryDirection(double fireSpeed, Agent agent, double windDirection);

    double calculateSpeedOfSpreadWithConstraint(ForestFuelTypeDao forestFuelTypeDao, double windSpeed);

    /**
     * This method calculate relief constraint for the speed of fire spread
     * @return value relief coefficient
     */
    BigDecimal reliefCoefficient(Node from, Node to);
}
