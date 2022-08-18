package git.niklogik.sim.components;

import git.niklogik.sim.Component;

/**
 * Environment state component
 */
public class EnvStateComponent implements Component {
    public final Double humidity;
    public final Double temperature;

    public EnvStateComponent(Double humidity, Double temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }
}
