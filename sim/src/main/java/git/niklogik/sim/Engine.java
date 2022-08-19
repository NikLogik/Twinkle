package git.niklogik.sim;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private final EntityManager entityManager = new EntityManager();
    private final SystemManager systemManager = new SystemManager();
    private final List<Node> nodesList = new ArrayList<>(16);

    public ImmutableList<System> allSystems(){
        return systemManager.allSystems();
    }

    public <T extends System> T getSystem(Class<T> systemType){
        return systemManager.findSystem(systemType);
    }

    public ImmutableList<Entity> allEntities(){
        return entityManager.allEntities();
    }

    public ImmutableList<Entity> entitiesByGroup(ComponentsGroup group){
        return entityManager.entitiesByGroup(group);
    }

    public void runTick(long deltaTime){
        for (System system : systemManager.prioritizedList()){
            system.update(deltaTime);
        }
    }
}
