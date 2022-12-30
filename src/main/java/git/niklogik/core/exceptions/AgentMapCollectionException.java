package git.niklogik.core.exceptions;

public class AgentMapCollectionException extends IllegalArgumentException {
    public AgentMapCollectionException(String s) {
        super(s);
    }

    public AgentMapCollectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
