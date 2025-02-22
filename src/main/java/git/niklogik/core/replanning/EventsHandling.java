package git.niklogik.core.replanning;



import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.lib.AgentState;
import git.niklogik.core.replanning.events.AfterIterationEvent;
import git.niklogik.core.replanning.events.AgentsChangePolygonEvent;
import git.niklogik.core.replanning.events.AgentsTooCloseMovedEvent;
import git.niklogik.core.replanning.events.AgentsTooFarMovedEvent;
import git.niklogik.core.replanning.events.BeforeIterationEvent;
import git.niklogik.core.replanning.events.SelfCrossingFireFrontEvent;
import git.niklogik.core.replanning.lib.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import git.niklogik.core.controller.lib.IterationInfo;

import java.util.LinkedList;
import java.util.Map;

/**
 * Принимает в конструктор EventManager, конфиг. Потом через методы handleWhenIterationEnd() получает состояние контроллера.
 * После чего нужные данные контроллера передаются в соответствующие хэндлеры, обрабатываются и возбуждаются определенные
 * события, через EventManager находится нужный хэндлер, передается туда событие.
 */
public final class EventsHandling {

    Logger logger = LoggerFactory.getLogger(EventsHandling.class);

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
        logger.info("Finished manage events with {} agents.", event.getInfo().getAgents().size());
    }

    public void persistAndReset(Map<Integer, LinkedList<AgentState>> twinkles){
        LinkedList<AgentState> listOfStates = FireUtils.getListOfStates(info.getAgents(), iterNum);
        twinkles.put(iterNum, listOfStates);
        eventManager.resetHandlers();
    }
}
