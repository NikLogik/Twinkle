package ru.nachos.core.fire.algorithms;

import org.springframework.data.geo.Polygon;
import ru.nachos.core.Coord;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.lib.Weather;

@Deprecated
public class Rotermel implements FireSpreadCalculator {

    private Network network;
    private Weather weather;

    public Rotermel(){}

    public Rotermel(Network network, Weather weather){
        this.network = network;
        this.weather = weather;
    }

    @Override
    public double calculateFreeSpeedOfSpread(Agent agent) {
        Coord source = agent.getCoord();
        double direction = agent.getDirection();
        return 0;
    }

    @Override
    public double calculateSpeedWithExternalConstraint(Agent agent) {
        return 0;
    }

    public static Id<Polygon> findPolygoneByAgentCoords(Coord coord){
        return null;
    }
}
