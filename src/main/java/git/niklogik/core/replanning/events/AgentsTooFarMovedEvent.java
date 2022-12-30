package git.niklogik.core.replanning.events;

import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.replanning.lib.Event;

public class AgentsTooFarMovedEvent extends Event {

    private IterationInfo info;

    public AgentsTooFarMovedEvent(int iterNum, IterationInfo info) {
        super(iterNum);
        this.info = info;
    }

    public IterationInfo getInfo() { return info; }

    @Override
    public String getEventType() {
        return "moved_away";
    }
}
