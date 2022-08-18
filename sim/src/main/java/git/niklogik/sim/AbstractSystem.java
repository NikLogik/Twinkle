package git.niklogik.sim;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSystem<T extends Node> implements System {

    protected final List<T> nodes;

    public AbstractSystem() {
        this(new ArrayList<>(16));
    }

    public AbstractSystem(List<T> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void update(long deltaTime) {
        for (T node : nodes){
            processNode(node, deltaTime);
        }
    }

    protected abstract void processNode(T node, long deltaTime);
}