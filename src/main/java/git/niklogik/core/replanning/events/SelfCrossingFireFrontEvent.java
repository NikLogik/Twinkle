package git.niklogik.core.replanning.events;

import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.replanning.lib.Event;

public class SelfCrossingFireFrontEvent extends Event {

    private IterationInfo info;

    public SelfCrossingFireFrontEvent(int iterNum, IterationInfo info) {
        super(iterNum);
        this.info = info;
    }

    public IterationInfo getInfo() { return this.info; }

    @Override
    public String getEventType() {
        return "self_crossing_event";
    }
}
