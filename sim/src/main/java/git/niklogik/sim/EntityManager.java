package git.niklogik.sim;

import com.google.common.collect.ImmutableList;
import git.niklogik.sim.entities.EntityID;

import java.util.HashMap;
import java.util.stream.Collectors;

public class EntityManager {
    private final HashMap<EntityID, Entity> entityMap = new HashMap<>(16);

    public boolean addEntity(Entity entity) {
        return entityMap.putIfAbsent(entity.getId(), entity) == null;
    }

    public boolean removeEntity(Entity entity){
        return entityMap.remove(entity.getId()) != null;
    }

    public ImmutableList<Entity> entitiesByGroup(ComponentsGroup group) {
        return ImmutableList.copyOf(
                entityMap.values()
                        .stream()
                        .filter(group::match)
                        .collect(Collectors.toList())
        );
    }

    public ImmutableList<Entity> allEntities() {
        return ImmutableList.copyOf(entityMap.values());
    }
}
