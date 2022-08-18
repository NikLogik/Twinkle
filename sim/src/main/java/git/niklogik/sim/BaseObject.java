package git.niklogik.sim;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseObject implements SimObject {

    public final EntityID unitId;
    private final Map<Class<? extends Component>, Component> components;

    public BaseObject() {
        unitId = new EntityID();
        components = new HashMap<>(6);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    @Override
    public BaseObject addComponent(final Component component) {
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

    public static class EntityID {
        public final UUID uuid;

        public EntityID() {
            this.uuid = UUID.randomUUID();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof EntityID)) return false;

            EntityID other = (EntityID) obj;
            return this.uuid.equals(other.uuid);
        }
    }
}
