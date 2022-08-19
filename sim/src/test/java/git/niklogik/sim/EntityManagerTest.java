package git.niklogik.sim;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import git.niklogik.sim.entities.BaseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityManagerTest {

    private EntityManager manager = new EntityManager();

    @Test
    @DisplayName("Add new entity")
    void addNewEntityTest(){
        BaseEntity baseEntity = new BaseEntity();

        assertThat(manager.addEntity(baseEntity)).isTrue();
        assertThat(manager.allEntities()).hasSize(1);

        assertThat(manager.addEntity(baseEntity)).isFalse();
    }

    @Test
    @DisplayName("Remove entity")
    void removeEntityTest(){
        BaseEntity baseEntity = new BaseEntity();

        manager.addEntity(baseEntity);

        assertThat(manager.removeEntity(baseEntity)).isTrue();
        assertThat(manager.allEntities()).hasSize(0);
        assertThat(manager.removeEntity(baseEntity)).isFalse();
    }

    @Test
    @DisplayName("Get entities by group")
    void getEntitiesByGroupTest(){
        BaseEntity targetEntity = new BaseEntity().addComponent(new TestComponent());
        BaseEntity emptyEntity = new BaseEntity();

        manager.addEntity(targetEntity);
        manager.addEntity(emptyEntity);

        ImmutableList<Entity> entities = manager.entitiesByGroup(new ComponentsGroup(ImmutableSet.of(TestComponent.class)));
        assertThat(entities).hasSize(1);

        ImmutableList<Entity> mustContainAllEntities = manager.entitiesByGroup(new ComponentsGroup(ImmutableSet.of()));
        assertThat(mustContainAllEntities).hasSize(2);

        ImmutableList<Entity> mustBeEmpty = manager.entitiesByGroup(new ComponentsGroup(ImmutableSet.of(AbsenceTestComponent.class)));
        assertThat(mustBeEmpty).isEmpty();
    }

    protected static class TestComponent implements Component{}

    protected static class AbsenceTestComponent implements Component{}
}