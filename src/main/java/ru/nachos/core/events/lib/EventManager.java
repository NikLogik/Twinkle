package ru.nachos.core.events.lib;

import ru.nachos.core.events.Event;

public interface EventManager {



    EventHandler findHandler(Event event);

    boolean callHandler(Event event, EventHandler handler);
}
