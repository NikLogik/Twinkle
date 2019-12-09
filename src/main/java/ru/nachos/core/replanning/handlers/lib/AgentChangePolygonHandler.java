package ru.nachos.core.replanning.handlers.lib;

import ru.nachos.core.replanning.events.AgentsChangePolygonEvent;
import ru.nachos.core.replanning.lib.EventHandler;

public interface AgentChangePolygonHandler extends EventHandler {
    void handleEvent(AgentsChangePolygonEvent event);
}
