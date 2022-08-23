package git.niklogik.sim.nodes;

import git.niklogik.sim.Node;
import git.niklogik.sim.components.PositionComponent;
import git.niklogik.sim.components.TransferComponent;
import git.niklogik.sim.entities.EntityID;
import org.jetbrains.annotations.NotNull;

public class MovementNode extends IdentifiableNode implements Node {
    public final TransferComponent transfer;
    public final PositionComponent position;

    public MovementNode(
            @NotNull EntityID id,
            @NotNull TransferComponent transfer,
            @NotNull PositionComponent position
    ) {
        super(id);
        this.transfer = transfer;
        this.position = position;
    }
}
