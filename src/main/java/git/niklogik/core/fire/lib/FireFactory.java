package git.niklogik.core.fire.lib;

import git.niklogik.core.Id;
import org.locationtech.jts.geom.Coordinate;

import java.util.Map;

public interface FireFactory {

    Agent createTwinkle(Id<Agent> id);

    Map<Id<Agent>, Agent> generateFireFront(Coordinate center, int distance, int perimeter, double startDirection);
}
