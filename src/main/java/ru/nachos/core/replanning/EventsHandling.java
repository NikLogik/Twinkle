package ru.nachos.core.replanning;


import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.replanning.events.*;
import ru.nachos.core.replanning.lib.EventManager;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Принимает в конструктор EventManager, конфиг. Потом через методы handleWhenIterationEnd() получает состояние контроллера.
 * После чего нужные данные контроллера передаются в соответствующие хэндлеры, обрабатываются и возбуждаются определенные
 * события, через EventManager находится нужный хэндлер, передается туда событие.
 */
public final class EventsHandling {

    private Config config;
    private EventManager eventManager;

    public EventsHandling(EventManager eventManager, Config config){
        this.config = config;
        this.eventManager = eventManager;
    }

    public void handleBeforeIterationStart(BeforeIterationEvent event){
        Controller controller = event.getController();
        Set<Id<Agent>> ids = controller.getFire().getTwinkles().values().stream()
                                                .filter(agent -> !agent.isStopped())
                                                .map(Agent::getId)
                                                .collect(Collectors.toSet());
        controller.getIterationMap().put(event.getIterNum(), ids);
    }

    public void handleAfterIterationEnd(AfterIterationEvent event){
        Controller controller = event.getController();
//        eventManager.computeEvent(new AgentsTooFarMovedEvent(event.getIterNum(), config.getFireAgentsDistance(), controller.getFire(), controller.getNetwork()));
        eventManager.computeEvent(new AgentsChangePolygonEvent(event.getIterNum(), controller));
        eventManager.computeEvent(new AgentsTooCloseMovedEvent(event.getIterNum(), config.getFireAgentsDistance() / 2, controller));
        eventManager.computeEvent(new AgentInsideFirefrontEvent(event.getIterNum(), controller));
        controller.getAgentsForIter(event.getIterNum()).values().forEach(agent -> agent.saveState(event.getIterNum()));
        resetAfterIteration();
    }

    private void resetAfterIteration(){
        eventManager.resetHandlers();
    }
}
