package ru.nachos.core.events.lib;

import ru.nachos.core.events.Event;

public interface EventHandler {
    void handleEvent(Event event);
}
