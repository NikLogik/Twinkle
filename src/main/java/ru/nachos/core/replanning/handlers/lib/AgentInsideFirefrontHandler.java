package ru.nachos.core.replanning.handlers.lib;

import ru.nachos.core.replanning.events.AgentInsideFirefrontEvent;
import ru.nachos.core.replanning.lib.EventHandler;

public interface AgentInsideFirefrontHandler extends EventHandler {
    void handleEvent(AgentInsideFirefrontEvent event);
}
