package ru.nachos.core.replanning.lib;

public interface EventManager {

    void computeEvent(Event event);

    void resetHandlers();
}
