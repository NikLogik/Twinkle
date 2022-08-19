package git.niklogik.sim;

import com.badlogic.gdx.utils.compression.lzma.Base;
import git.niklogik.sim.entities.BaseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseEntityTest {

    @Test
    @DisplayName("Generate new ID on each instance")
    void generateIdEachInstanceTest(){
        BaseEntity firstEntity = new BaseEntity();
        assertThat(firstEntity.getId()).isNotNull();

        BaseEntity secondEntity = new BaseEntity();
        assertThat(secondEntity.getId()).isNotNull();

        assertThat(firstEntity.getId()).isNotEqualTo(secondEntity.getId());

    }

    @Test
    @DisplayName("Add new component")
    void addComponentTest(){
        String testComponentValue = "first value";

        BaseEntity baseEntity = new BaseEntity();
        baseEntity.addComponent(new TestComponent(testComponentValue));

        TestComponent component = baseEntity.getComponent(TestComponent.class);
        assertThat(component).isNotNull();
        assertThat(component.someValue).isNotBlank().isEqualTo(testComponentValue);
    }

    @Test
    @DisplayName("Add number of the components")
    void addNumberComponentsTest(){
        BaseEntity baseEntity = new BaseEntity();

        TestComponent component = new TestComponent("component value");
        OtherTestComponent otherComponent = new OtherTestComponent(1023);

        baseEntity.addComponent(component).addComponent(otherComponent);

        assertThat(baseEntity.getComponents()).hasSize(2);
        assertThat(baseEntity.getComponents()).contains(component, otherComponent);
    }

    @Test
    @DisplayName("Replace existing component")
    void replaceExistingComponentTest(){

        TestComponent initial = new TestComponent("component value");
        TestComponent last = new TestComponent("replacing value");

        BaseEntity baseEntity = new BaseEntity().addComponent(initial).addComponent(last);

        assertThat(baseEntity.getComponents()).hasSize(1);

        TestComponent component = baseEntity.getComponent(TestComponent.class);
        assertThat(component).isNotNull();
        assertThat(component.someValue).isNotBlank().isEqualTo(last.someValue);
    }

    @Test
    @DisplayName("Get optional absence component")
    void getOptionalAbsenceComponentTest() {

        BaseEntity baseEntity = new BaseEntity();

        Optional<OtherTestComponent> componentNullable = baseEntity.getComponentNullable(OtherTestComponent.class);
        assertThat(componentNullable).isNotPresent();
    }

    @Test
    @DisplayName("Get absence component exceptionally")
    void getAbsenceComponentExceptionally(){

        final BaseEntity baseEntity = new BaseEntity();

        assertThrows(ComponentAbsenceException.class, () -> {
           baseEntity.getComponentNN(OtherTestComponent.class);
        });
    }

    private static class TestComponent implements Component {
        public final String someValue;

        public TestComponent(String someValue) {
            this.someValue = someValue;
        }
    }

    private static class OtherTestComponent implements Component, com.badlogic.ashley.core.Component {
        public final Integer otherValue;

        public OtherTestComponent(Integer otherValue) {
            this.otherValue = otherValue;
        }
    }
}