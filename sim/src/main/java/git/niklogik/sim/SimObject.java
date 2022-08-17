package git.niklogik.sim;

import com.google.common.collect.ImmutableList;

public interface SimObject {
    <T extends Component> T getComponent(Class<T> componentClass);

    SimObject addComponent(Component component);

    ImmutableList<Component> getComponents();
}
