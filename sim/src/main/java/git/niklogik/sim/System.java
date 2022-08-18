package git.niklogik.sim;

public interface System {
    void update(long deltaTime);
    System applyEngine(Engine engine);
}
