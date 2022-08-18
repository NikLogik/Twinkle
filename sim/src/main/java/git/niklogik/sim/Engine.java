package git.niklogik.sim;

import com.google.common.collect.ImmutableList;

public interface Engine {
    ImmutableList<SimObject> getObjects();
    ImmutableList<System> getSystems();
}
