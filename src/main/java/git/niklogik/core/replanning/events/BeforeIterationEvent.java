package git.niklogik.core.replanning.events;

import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.replanning.lib.Event;

public class BeforeIterationEvent extends Event {

    private IterationInfo info;

    public BeforeIterationEvent(IterationInfo info) {
        super(info.getIterNum());
        this.info = info;
    }

    @Override
    public String getEventType() {
        return "iteration_start_event";
    }

    public IterationInfo getInfo() { return info; }
}
