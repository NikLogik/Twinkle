package git.niklogik.sim;

public interface NodeMapper<N extends Node> {
    N toNode(Entity entity) throws NodeMappingException;
}
