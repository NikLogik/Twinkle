package ru.nachos.core.replanning.handlers.lib;

import ru.nachos.core.replanning.events.AgentsTooFarMovedEvent;
import ru.nachos.core.replanning.lib.EventHandler;

public interface AgentTooFarMovedHandler extends EventHandler {
    void handleEvent(AgentsTooFarMovedEvent event);
}
