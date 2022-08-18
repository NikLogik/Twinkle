package git.niklogik.sim.nodes;

import git.niklogik.sim.Node;
import git.niklogik.sim.components.NeighbourComponent;
import git.niklogik.sim.components.PositionComponent;

public class NeighbourDistanceNode implements Node {
    public final PositionComponent position;
    public final NeighbourComponent neighbourComponent;

    public NeighbourDistanceNode(PositionComponent position, NeighbourComponent neighbourComponent) {
        this.position = position;
        this.neighbourComponent = neighbourComponent;
    }
}
