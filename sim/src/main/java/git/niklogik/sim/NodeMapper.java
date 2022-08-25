package git.niklogik.sim;

/**
 * Maps {@link Entity} object into specific {@link Node} type.
 * @param <N> specific Node type
 *
 * @see ComponentsGroup for checking that entity contains node`s specific all components
 *
 * @author niklogik
 */
public interface NodeMapper<N extends Node> {
    N toNode(Entity entity) throws NodeMappingException;
}
