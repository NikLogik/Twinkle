package ru.nachos.core.replanning;


import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.replanning.events.AfterIterationEvent;
import ru.nachos.core.replanning.events.AgentsChangePolygonEvent;
import ru.nachos.core.replanning.events.AgentsTooFarMovedEvent;
import ru.nachos.core.replanning.lib.EventManager;

/**
 * Принимает в конструктор EventManager, конфиг. Потом через методы handleWhenIterationEnd() получает состояние контроллера.
 * После чего нужные данные контроллера передаются в соответствующие хэндлеры, обрабатываются и возбуждаются определенные
 * события, через EventManager находится нужный хэндлер, передается туда событие.
 */
public final class EventsHandling {

    private Config config;
    private EventManager eventManager;
    private Controller controller;

    public EventsHandling(EventManager eventManager, Config config){
        this.config = config;
        this.eventManager = eventManager;
    }

    public void handleAfterIterationEnd(AfterIterationEvent event){
        this.controller = event.getController();
        if (event.getIterNum()!=0) {
            double distance = config.getFireAgentsDistance();
            eventManager.computeEvent(new AgentsTooFarMovedEvent(event.getIterNum(), config.getFireAgentsDistance(), controller.getFire()));
            eventManager.computeEvent(new AgentsChangePolygonEvent(event.getIterNum(), controller.getNetwork(), controller.getFire()));

            resetAfterIteration();
        }
    }

    private void resetAfterIteration(){
        this.controller = null;
        eventManager.resetHandlers();
    }
}
