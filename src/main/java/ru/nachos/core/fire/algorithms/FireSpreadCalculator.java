package ru.nachos.core.fire.algorithms;


import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.network.ForestFuelType;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.lib.Weather;

public interface FireSpreadCalculator {

    void calculateSpeedOfSpreadWithArbitraryDirection(double fireSpeed, Agent agent);

    /**
     * This method calculate speed for concrete agent,
     * considering external weather and relief conditions
     * @param speed
     * @return speed of fire spread for concrete agent
     */
    double calculateSpeedOfSpreadWithConstraint(double speed, boolean reliefData);

    /**
     * This method calculate speed of fire spread without external constraint, as relief,
     * wind direction, wind speed and return speed in meters/minute
     * @param type - type of a forest fuel, which is burning
     * @return speed of fire spread without external constraint
     */
    double calculateSpeedWithoutExternalConstraint(ForestFuelType type);

    void setNetwork(Network network);

    void setWeather(Weather weather);
}
