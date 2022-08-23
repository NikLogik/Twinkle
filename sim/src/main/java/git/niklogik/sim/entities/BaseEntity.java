package git.niklogik.sim.entities;

import com.google.common.collect.ImmutableList;
import git.niklogik.sim.Component;
import git.niklogik.sim.Entity;

import java.util.HashMap;
import java.util.Map;

public class BaseEntity implements Entity {

    public final EntityID id;
    private final Map<Class<? extends Component>, Component> components;

    public BaseEntity() {
        id = new EntityID();
        components = new HashMap<>(6);
    }

    @Override
    public EntityID getId() {
        return this.id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    @Override
    public BaseEntity addComponent(final Component component) {
        if (addNewComponent(component)) return this;
        Component oldComponent = components.get(component.getClass());

        if (component == oldComponent) return this;

        components.put(component.getClass(), component);
        return this;
    }

    @Override
    public ImmutableList<Component> getComponents() {
        return ImmutableList.copyOf(this.components.values());
    }

    boolean addNewComponent(Component component){
        return components.putIfAbsent(component.getClass(), component) == null;
    }
}
