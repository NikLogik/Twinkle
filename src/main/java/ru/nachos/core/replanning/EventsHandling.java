package ru.nachos.core.replanning;


import org.apache.log4j.Logger;
import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.replanning.events.*;
import ru.nachos.core.replanning.lib.EventManager;

import java.util.LinkedList;
import java.util.Map;

/**
 * Принимает в конструктор EventManager, конфиг. Потом через методы handleWhenIterationEnd() получает состояние контроллера.
 * После чего нужные данные контроллера передаются в соответствующие хэндлеры, обрабатываются и возбуждаются определенные
 * события, через EventManager находится нужный хэндлер, передается туда событие.
 */
public final class EventsHandling {

    Logger logger = Logger.getLogger(EventsHandling.class);

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
        logger.info("Start manage events after iteration #" + event.getIterNum() + " with " + event.getInfo().getAgents().size() + " agents.");
        this.info = event.getInfo();
        this.iterNum = event.getIterNum();
        eventManager.computeEvent(new AgentsChangePolygonEvent(iterNum, info));
        eventManager.computeEvent(new AgentsTooFarMovedEvent(iterNum, info));
        eventManager.computeEvent(new SelfCrossingFireFrontEvent(iterNum, info));
        eventManager.computeEvent(new AgentsTooCloseMovedEvent(iterNum, info));
        info.getAgents().forEach(agent -> agent.saveState(iterNum));
        logger.info("Finished manage events with " + event.getInfo().getAgents().size() + " agents.");
    }

    public void persistAndReset(Map<Integer, LinkedList<AgentState>> twinkles){
        LinkedList<AgentState> listOfStates = FireUtils.getListOfStates(info.getAgents(), iterNum);
        twinkles.put(iterNum, listOfStates);
        eventManager.resetHandlers();
    }
}
