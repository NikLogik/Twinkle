package git.niklogik.sim.components;

import git.niklogik.sim.Component;

public class ActivityStateComponent implements Component {

    public final ActivityState state;

    public ActivityStateComponent(ActivityState state) {
        this.state = state;
    }

    public enum ActivityState {
        ACTIVE, PASSIVE, STOPPED;
    }
}
