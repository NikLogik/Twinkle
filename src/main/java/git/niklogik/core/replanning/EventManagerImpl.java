package git.niklogik.core.replanning;

import git.niklogik.core.replanning.events.AgentsChangePolygonEvent;
import git.niklogik.core.replanning.events.AgentsTooCloseMovedEvent;
import git.niklogik.core.replanning.events.AgentsTooFarMovedEvent;
import git.niklogik.core.replanning.events.SelfCrossingFireFrontEvent;
import git.niklogik.core.replanning.handlers.AgentChangePolygonHandler;
import git.niklogik.core.replanning.handlers.AgentTooCloseMovedHandler;
import git.niklogik.core.replanning.handlers.SelfCrossingFireFrontHandler;
import git.niklogik.core.replanning.handlers.AgentTooFarMovedHandler;
import git.niklogik.core.replanning.lib.Event;
import git.niklogik.core.replanning.lib.EventHandler;
import git.niklogik.core.replanning.lib.EventManager;

import java.util.HashMap;
import java.util.Map;

public class EventManagerImpl implements EventManager {

    private static final Map<Class<? extends Event>, EventHandler> handlerMap = new HashMap<>();

    public EventManagerImpl() {
        handlerMap.put(AgentsChangePolygonEvent.class, new AgentChangePolygonHandler());
        handlerMap.put(AgentsTooCloseMovedEvent.class, new AgentTooCloseMovedHandler());
        handlerMap.put(AgentsTooFarMovedEvent.class, new AgentTooFarMovedHandler());
        handlerMap.put(SelfCrossingFireFrontEvent.class, new SelfCrossingFireFrontHandler());
    }

    @Override
    public void computeEvent(Event event) {
        if (handlerMap.containsKey(event.getClass())) {
            EventHandler eventHandler = handlerMap.get(event.getClass());
            if (eventHandler != null) {
                eventHandler.handleEvent(event);
            }
        }
    }

    @Override
    public void resetHandlers() {
        handlerMap.values().forEach(EventHandler::resetHandler);
    }
}
