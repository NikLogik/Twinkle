package git.niklogik.sim;

public interface System {
    int priority();
    void update(long deltaTime);
    System applyEngine(Engine engine);
}
