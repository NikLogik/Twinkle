package ru.nachos.core.replanning.lib;

import ru.nachos.core.replanning.Event;

public interface EventManager {

    void computeEvent(Event event);

    void resetHandlers();
}
