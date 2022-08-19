package git.niklogik.sim.systems;

import git.niklogik.sim.Node;
import git.niklogik.sim.System;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSystem<T extends Node> implements System {

    protected List<T> nodes;
    private final int priority;

    public AbstractSystem() {
        this(new ArrayList<>(16), 0);
    }

    public AbstractSystem(int priority) {
        this(new ArrayList<>(16), priority);
    }

    public AbstractSystem(List<T> nodes, int priority) {
        this.nodes = nodes;
        this.priority = priority;
    }

    @Override
    public final int priority() {
        return this.priority;
    }

    @Override
    public void update(long deltaTime) {
        for (T node : nodes){
            processNode(node, deltaTime);
        }
    }

    protected abstract void processNode(T node, long deltaTime);
}