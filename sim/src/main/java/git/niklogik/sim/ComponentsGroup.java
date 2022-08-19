package git.niklogik.sim;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

public class ComponentsGroup {

    public final ImmutableSet<Class<? extends Component>> componentTypes;

    public ComponentsGroup(ImmutableSet<Class<? extends Component>> componentTypes) {
        this.componentTypes = componentTypes;
    }

    public boolean match(Entity object){
        Set<Class<? extends Component>> set = new HashSet<>();
        for (Component c : object.getComponents()) {
            set.add(c.getClass());
        }
        return set.containsAll(componentTypes);
    }
}
