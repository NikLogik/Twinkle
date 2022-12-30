package git.niklogik.sim;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface Entity extends Identifiable {
    @Nullable
    <T extends Component> T getComponent(Class<T> componentClass);

    default <T extends Component> Optional<T> getComponentNullable(Class<T> componentClass){
        return Optional.ofNullable(getComponent(componentClass));
    }

    @NotNull
    default <T extends Component> T getComponentNN(Class<T> componentClass){
        return getComponentNullable(componentClass).orElseThrow(() -> new ComponentAbsenceException(componentClass));
    }

    Entity addComponent(Component component);

    ImmutableList<Component> getComponents();
}
