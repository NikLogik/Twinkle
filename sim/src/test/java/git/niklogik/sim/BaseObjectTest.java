package git.niklogik.sim;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaseObjectTest {

    @Test
    @DisplayName("Add new component")
    void addComponentTest(){
        String testComponentValue = "first value";

        BaseObject baseObject = new BaseObject();
        baseObject.addComponent(new TestComponent(testComponentValue));

        TestComponent component = baseObject.getComponent(TestComponent.class);
        assertThat(component).isNotNull();
        assertThat(component.someValue).isNotBlank().isEqualTo(testComponentValue);
    }

    @Test
    @DisplayName("Add number of the components")
    void addNumberComponentsTest(){
        BaseObject baseObject = new BaseObject();

        TestComponent component = new TestComponent("component value");
        OtherTestComponent otherComponent = new OtherTestComponent(1023);

        baseObject.addComponent(component).addComponent(otherComponent);

        assertThat(baseObject.getComponents()).hasSize(2);
        assertThat(baseObject.getComponents()).contains(component, otherComponent);
    }

    @Test
    @DisplayName("Replace existing component")
    void replaceExistingComponentTest(){

        TestComponent initial = new TestComponent("component value");
        TestComponent last = new TestComponent("replacing value");

        BaseObject baseObject = new BaseObject().addComponent(initial).addComponent(last);

        assertThat(baseObject.getComponents()).hasSize(1);

        TestComponent component = baseObject.getComponent(TestComponent.class);
        assertThat(component).isNotNull();
        assertThat(component.someValue).isNotBlank().isEqualTo(last.someValue);
    }

    @Test
    @DisplayName("Replace parent component by its child")
    void replaceParentComponentTest(){

        Integer value = 10;

        BaseObject baseObject = new BaseObject().addComponent(new OtherTestComponent(value)).addComponent(new ChildOtherComponent(value));

        assertThat(baseObject.getComponents()).hasSize(1);

    }

    private static class TestComponent implements Component {
        public final String someValue;

        public TestComponent(String someValue) {
            this.someValue = someValue;
        }
    }

    private static class OtherTestComponent implements Component {
        public final Integer otherValue;

        public OtherTestComponent(Integer otherValue) {
            this.otherValue = otherValue;
        }
    }

    private static class ChildOtherComponent extends OtherTestComponent {

        public final String childValue = "Child component value";

        public ChildOtherComponent(Integer otherValue) {
            super(otherValue);
        }
    }
}