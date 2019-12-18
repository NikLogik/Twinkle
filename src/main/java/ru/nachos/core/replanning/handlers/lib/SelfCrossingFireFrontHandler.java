package ru.nachos.core.replanning.handlers.lib;

import ru.nachos.core.replanning.events.SelfCrossingFireFrontEvent;
import ru.nachos.core.replanning.lib.EventHandler;

public interface SelfCrossingFireFrontHandler extends EventHandler {
    void handleEvent(SelfCrossingFireFrontEvent event);
}
