package git.niklogik.core.fire.algorithms;


import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.network.lib.ForestFuelType;
import git.niklogik.core.network.lib.Node;

public interface FireSpreadCalculator {

    void calculateSpeedOfSpreadWithArbitraryDirection(double fireSpeed, Agent agent, double windDirection);

    /**
     * This method calculate speed for concrete agent,
     * considering external weather and relief conditions
     * @param speed
     * @param windSpeed
     * @return speed of fire spread for concrete agent
     */
    double calculateSpeedOfSpreadWithConstraint(double speed, double windSpeed);

    /**
     * This method calculate speed of fire spread without external constraint, as relief,
     * wind direction, wind speed and return speed in meters/minute
     * @param type - type of a forest fuel, which is burning
     * @return speed of fire spread without external constraint
     */
    double calculateSpeedWithoutExternalConstraint(ForestFuelType type);

    /**
     * This method calculate relief constraint for the speed of fire spread
     * @return value relief coefficient
     */
    double reliefCoefficient(Node from, Node to);
}
