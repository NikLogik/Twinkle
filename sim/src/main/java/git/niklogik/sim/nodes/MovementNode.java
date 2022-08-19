package git.niklogik.sim.nodes;

import git.niklogik.sim.Node;
import git.niklogik.sim.components.PositionComponent;
import git.niklogik.sim.components.TransferComponent;
import org.jetbrains.annotations.NotNull;

public class MovementNode implements Node {
    public final TransferComponent transfer;
    public final PositionComponent position;

    public MovementNode(@NotNull TransferComponent transfer, PositionComponent position) {
        this.transfer = transfer;
        this.position = position;
    }
}
