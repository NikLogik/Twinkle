package git.niklogik.core.replanning.events;

import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.replanning.lib.Event;

public class AgentsChangePolygonEvent extends Event {

    private IterationInfo info;

    public AgentsChangePolygonEvent(int iterNum, IterationInfo info) {
        super(iterNum);
        this.info = info;
    }

    @Override
    public String getEventType() {
        return "stopped";
    }

    public IterationInfo getInfo() { return info; }
}
