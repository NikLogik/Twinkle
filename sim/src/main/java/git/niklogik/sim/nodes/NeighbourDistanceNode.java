package git.niklogik.sim.nodes;

import git.niklogik.sim.Node;
import git.niklogik.sim.components.NeighbourComponent;
import git.niklogik.sim.components.PositionComponent;
import git.niklogik.sim.entities.EntityID;

public class NeighbourDistanceNode extends IdentifiableNode implements Node {
    public final PositionComponent position;
    public final NeighbourComponent neighbourComponent;

    public NeighbourDistanceNode(EntityID id, PositionComponent position, NeighbourComponent neighbourComponent) {
        super(id);
        this.position = position;
        this.neighbourComponent = neighbourComponent;
    }
}
