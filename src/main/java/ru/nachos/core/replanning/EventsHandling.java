package ru.nachos.core.replanning;


import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.replanning.events.AfterIterationEvent;
import ru.nachos.core.replanning.events.AgentsChangePolygonEvent;
import ru.nachos.core.replanning.events.AgentsTooCloseMovedEvent;
import ru.nachos.core.replanning.events.BeforeIterationEvent;
import ru.nachos.core.replanning.lib.EventManager;
import ru.nachos.core.utils.AgentMap;

/**
 * Принимает в конструктор EventManager, конфиг. Потом через методы handleWhenIterationEnd() получает состояние контроллера.
 * После чего нужные данные контроллера передаются в соответствующие хэндлеры, обрабатываются и возбуждаются определенные
 * события, через EventManager находится нужный хэндлер, передается туда событие.
 */
public final class EventsHandling {

//    Logger logger = Logger.getLogger(EventsHandling.class);

    private IterationInfo info;
    private EventManager eventManager;
    private int iterNum;

    public EventsHandling(EventManager eventManager){
        this.eventManager = eventManager;
    }

    public void handleBeforeIterationStart(BeforeIterationEvent event){
        this.iterNum = info.getIterNum();
    }

    public void handleAfterIterationEnd(AfterIterationEvent event){
//        logger.info("Start manage events after iteration #" + event.getIterNum() + " with " + event.getInfo().getAgents().size() + " agents.");
        this.info = event.getInfo();
        this.iterNum = event.getIterNum();
        //        eventManager.computeEvent(new AgentsTooFarMovedEvent(event.getIterNum(), config.getFireAgentsDistance(), controller.getFire(), controller.getNetwork()));
        eventManager.computeEvent(new AgentsChangePolygonEvent(iterNum, info));
        eventManager.computeEvent(new AgentsTooCloseMovedEvent(event.getIterNum(), info.getAgentDistance() / 2, info.getAgents()));
//        eventManager.computeEvent(new AgentInsideFirefrontEvent(event.getIterNum(), info.getGeomFactory(), info.getAgents(), info.getPolygons()));
        info.getAgents().forEach(agent -> agent.saveState(iterNum));
//        logger.info("Finished manage events with " + event.getInfo().getAgents().size() + " agents.");
    }

    public void persistAndReset(AgentMap twinkles){
        twinkles.merge(info.getAgents());
        twinkles.checkSequence();
        eventManager.resetHandlers();
    }
}
