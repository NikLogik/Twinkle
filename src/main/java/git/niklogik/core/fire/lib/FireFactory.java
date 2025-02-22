package git.niklogik.core.fire.lib;

import git.niklogik.core.Id;
import org.locationtech.jts.geom.Coordinate;

import java.util.Map;
import java.util.UUID;

public interface FireFactory {

    Agent createTwinkle(String agentPrefixId);

    Map<UUID, Agent> generateFireFront(Coordinate center, int distance, int perimeter, double startDirection);
}
