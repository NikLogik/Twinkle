package git.niklogik.sim.systems;

import com.google.common.collect.ImmutableSet;
import git.niklogik.sim.*;
import git.niklogik.sim.components.PositionComponent;
import git.niklogik.sim.components.TransferComponent;
import git.niklogik.sim.NodeMapper;
import git.niklogik.sim.NodeMappingException;
import git.niklogik.sim.nodes.MovementNode;

import java.util.stream.Collectors;

public class MovementSystem extends AbstractSystem<MovementNode> {

    private final ComponentsGroup group = new ComponentsGroup(ImmutableSet.of(TransferComponent.class, PositionComponent.class));

    public MovementSystem(int priority) {
        super(priority);
    }

    @Override
    protected void processNode(MovementNode node, long deltaTime) {
        throw new UnsupportedOperationException("Not implemented operation");
    }

    @Override
    public MovementSystem applyEngine(Engine engine) {
        MovementNodeMapper nodeMapper = new MovementNodeMapper();
        this.nodes = engine.entitiesByGroup(group).stream().map(nodeMapper::toNode).collect(Collectors.toList());
        return this;
    }

    protected static class MovementNodeMapper implements NodeMapper<MovementNode> {

        @Override
        public MovementNode toNode(Entity entity) throws NodeMappingException {
            return new MovementNode(
                    entity.getId(),
                    entity.getComponentNN(TransferComponent.class),
                    entity.getComponentNN(PositionComponent.class)
            );
        }
    }
}
