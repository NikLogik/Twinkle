package git.niklogik.core.replanning.lib;

public interface EventHandler {
    void handleEvent(Event event);
    void resetHandler();
    void persistEvents();
}
