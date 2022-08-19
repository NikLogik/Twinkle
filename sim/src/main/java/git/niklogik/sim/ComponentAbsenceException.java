package git.niklogik.sim;

public class ComponentAbsenceException extends RuntimeException {

    public ComponentAbsenceException(Class<? extends Component> absenceType) {
        super("Component absent by type: " + absenceType.getName());
    }
}
