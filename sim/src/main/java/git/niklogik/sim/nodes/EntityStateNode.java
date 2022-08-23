package git.niklogik.sim.nodes;

import git.niklogik.sim.Node;
import git.niklogik.sim.components.ActivityStateComponent;
import git.niklogik.sim.components.PositionComponent;
import git.niklogik.sim.entities.EntityID;
import org.jetbrains.annotations.NotNull;

public class EntityStateNode extends IdentifiableNode implements Node {
    public final PositionComponent position;
    public final ActivityStateComponent activityState;

    public EntityStateNode(
            @NotNull EntityID id,
            @NotNull PositionComponent position,
            @NotNull ActivityStateComponent activityState
    ) {
        super(id);
        this.position = position;
        this.activityState = activityState;
    }
}
