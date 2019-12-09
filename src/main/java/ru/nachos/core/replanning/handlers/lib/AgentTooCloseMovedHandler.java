package ru.nachos.core.replanning.handlers.lib;

import ru.nachos.core.replanning.events.AgentsTooCloseMovedEvent;
import ru.nachos.core.replanning.lib.EventHandler;

public interface AgentTooCloseMovedHandler extends EventHandler {
    void handleEvent(AgentsTooCloseMovedEvent event);
}
