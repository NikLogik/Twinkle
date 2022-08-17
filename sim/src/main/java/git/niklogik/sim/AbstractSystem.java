package git.niklogik.sim;

public abstract class AbstractSystem implements System {
    public void update(Component component) {
        if (isSupported(component)) process(component);
    }

    protected abstract boolean isSupported(Component component);

    protected abstract void process(Component component);
}