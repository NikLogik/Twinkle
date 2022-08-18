package git.niklogik.sim;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

public class ComponentsGroup {

    private final ImmutableSet<Class<? extends Component>> componentTypes;

    public ComponentsGroup(ImmutableSet<Class<? extends Component>> componentTypes) {
        this.componentTypes = componentTypes;
    }

    public boolean match(SimObject object){
        Set<Class<? extends Component>> set = new HashSet<>();
        for (Component c : object.getComponents()) {
            if (c.getClass().isAssignableFrom(Component.class)) {
                Class<? extends Component> aClass = c.getClass();
                set.add(aClass);
            }
        }
        return set.containsAll(componentTypes);
    }
}
