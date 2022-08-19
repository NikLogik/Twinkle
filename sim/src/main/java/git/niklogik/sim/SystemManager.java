package git.niklogik.sim;

import com.google.common.collect.ImmutableList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class SystemManager {

    private final Comparator<System> comparator = Comparator.comparingInt(System::priority);

    private final HashMap<Class<? extends System>, System> systemMap = new HashMap<>(16);

    public boolean addSystem(System system){
        return systemMap.putIfAbsent(system.getClass(), system) == null;
    }

    public boolean removeSystem(System system){
        return systemMap.remove(system.getClass()) != null;
    }

    @SuppressWarnings("unchecked")
    public <T extends System> T findSystem(Class<T> systemType){
        return (T) systemMap.get(systemType);
    }

    public ImmutableList<System> allSystems(){
        return ImmutableList.copyOf(systemMap.values());
    }

    public ImmutableList<System> prioritizedList() {
        return ImmutableList.copyOf(
                systemMap.values()
                        .stream()
                        .sorted(comparator)
                        .collect(Collectors.toList())
        );
    }
}
