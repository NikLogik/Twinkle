package ru.nachos.core.fire.algorithms;


import ru.nachos.core.fire.lib.Agent;

public interface FireSpreadCalculator {

    double calculateFreeSpeedOfSpread(Agent agent);

    double calculateSpeedWithExternalConstraint(Agent agent);

}
