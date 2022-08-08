package ru.nachos.core.fire.lib;

import org.locationtech.jts.geom.Coordinate;
import ru.nachos.core.Id;

import java.util.Map;

public interface FireFactory {

    Agent createTwinkle(Id<Agent> id);

    Map<Id<Agent>, Agent> generateFireFront(Coordinate center, int distance, int perimeter, double startDirection);
}
