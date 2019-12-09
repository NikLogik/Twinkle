package ru.nachos.core.replanning;

import ru.nachos.core.replanning.events.AgentsChangePolygonEvent;
import ru.nachos.core.replanning.events.AgentsTooCloseMovedEvent;
import ru.nachos.core.replanning.events.AgentsTooFarMovedEvent;
import ru.nachos.core.replanning.handlers.AgentChangePolygonHandlerImpl;
import ru.nachos.core.replanning.handlers.AgentTooCloseMovedHandlerImpl;
import ru.nachos.core.replanning.handlers.AgentTooFarMovedHandlerImpl;
import ru.nachos.core.replanning.handlers.lib.AgentChangePolygonHandler;
import ru.nachos.core.replanning.handlers.lib.AgentTooCloseMovedHandler;
import ru.nachos.core.replanning.handlers.lib.AgentTooFarMovedHandler;
import ru.nachos.core.replanning.lib.EventHandler;
import ru.nachos.core.replanning.lib.EventManager;

import java.util.HashMap;
import java.util.Map;

public class EventManagerImpl implements EventManager {

    private static final Map<Class<? extends Event>, EventHandler> handlerMap = new HashMap<>();

    public EventManagerImpl() {
        handlerMap.put(AgentsChangePolygonEvent.class, new AgentChangePolygonHandlerImpl());
        handlerMap.put(AgentsTooCloseMovedEvent.class, new AgentTooCloseMovedHandlerImpl());
        handlerMap.put(AgentsTooFarMovedEvent.class, new AgentTooFarMovedHandlerImpl());
    }

    @Override
    public void computeEvent(Event event) {
        if (handlerMap.containsKey(event.getClass())){
            EventHandler eventHandler = handlerMap.get(event.getClass());
            if (eventHandler != null){
                callHandler(event.getClass(), event, eventHandler);
            }
        }
    }

    private boolean callHandler(Class<? extends Event> clazz, Event event, EventHandler handler){
        if (clazz == AgentsChangePolygonEvent.class){
            ((AgentChangePolygonHandler)handler).handleEvent((AgentsChangePolygonEvent)event);
            return true;
        } else if (clazz == AgentsTooCloseMovedEvent.class){
            ((AgentTooCloseMovedHandler)handler).handleEvent((AgentsTooCloseMovedEvent)event);
            return true;
        } else if (clazz == AgentsTooFarMovedEvent.class){
            ((AgentTooFarMovedHandler)handler).handleEvent((AgentsTooFarMovedEvent) event);
            return true;
        }
        return false;
    }

    @Override
    public void resetHandlers() {
        handlerMap.values().forEach(EventHandler::resetHandler);
    }
}
