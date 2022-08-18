package git.niklogik.sim.systems;

import git.niklogik.sim.AbstractSystem;
import git.niklogik.sim.Engine;
import git.niklogik.sim.nodes.MovementNode;

import java.util.List;

public class MovementSystem extends AbstractSystem<MovementNode> {

    public MovementSystem(List<MovementNode> nodes) {
        super(nodes);
    }

    @Override
    protected void processNode(MovementNode node, long deltaTime) {

    }

    @Override
    public MovementSystem applyEngine(Engine engine) {
        throw new UnsupportedOperationException("Not implemented operation");
    }
}
