package git.niklogik.sim.nodes;

import git.niklogik.sim.Node;
import git.niklogik.sim.components.ActivityStateComponent;
import git.niklogik.sim.components.PositionComponent;

public class EntityStateNode implements Node {
    public final PositionComponent position;
    public final ActivityStateComponent activityState;

    public EntityStateNode(PositionComponent position, ActivityStateComponent activityState) {
        this.position = position;
        this.activityState = activityState;
    }
}
