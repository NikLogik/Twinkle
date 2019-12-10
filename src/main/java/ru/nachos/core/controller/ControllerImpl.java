package ru.nachos.core.controller;

import com.vividsolutions.jts.geom.Coordinate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.EventManagerImpl;
import ru.nachos.core.replanning.EventsHandling;
import ru.nachos.core.replanning.events.AfterIterationEvent;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.db.PolygonRepositoryImpl;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Component
class ControllerImpl implements Controller {

    private static Logger logger = Logger.getLogger(ControllerImpl.class);

    @Autowired
    private PolygonRepositoryImpl polygonRepository;

    private Network network;

    private Config config;
    private InitialPreprocessingData preprocessingData;
    private Fire fire;
    private EventsHandling eventsHandler;
    private Map<Integer, Set<Id<Agent>>> iterationMap = new TreeMap<>();
    public static final String DIVIDER = "###################################################";

    final String MARKER = "#####";

    private int currentIteration;
    private double currentTime;
    private double stepAmount;
    ControllerImpl(){}

    ControllerImpl(InitialPreprocessingData preprocessing){
        this.config = preprocessing.getConfig();
        this.preprocessingData = preprocessing;
        this.network = preprocessing.getNetwork();
        this.currentTime = config.getStartTime();
        this.stepAmount = config.getStepTimeAmount();
        this.fire = preprocessing.getFire();
        this.eventsHandler = new EventsHandling(new EventManagerImpl(), preprocessing.getConfig());
    }

    @Override
    public InitialPreprocessingData getPreprocessingData() { return this.preprocessingData; }

    @Override
    public Config getConfig() { return this.config; }

    @Override
    public void run(){
        doIteration();
    }

    private void doIteration(){
        logger.info("Start doing iterations");
        for (int start = config.getFirstIteration(); start < config.getLastIteration(); start++){
            this.iteration(start);
        }
    }

    private void iteration(int iteration) {
        this.currentIteration = iteration;

        logger.info(DIVIDER);
        logger.info("Iteration #" + currentIteration + " begin");

        currentTime += stepAmount;
        long timeStart = System.currentTimeMillis();
        logger.info("Move agents to new locations");
        iterationStep();
        AfterIterationEvent event = new AfterIterationEvent(this, currentIteration);
        eventsHandler.handleAfterIterationEnd(event);
        fire.getTwinkles().values().forEach(agent -> agent.saveState(currentIteration));
        iterationMap.put(currentIteration, fire.getTwinkles().keySet());

        long timePerIteration = System.currentTimeMillis() - timeStart;
        logger.info(DIVIDER);
        logger.info("Iteration #" + currentIteration + " finished");
    }

    private void iterationStep(){
        Map<Id<Agent>, Agent> agents = this.fire.getTwinkles();
        for (Agent agent : agents.values()){
            if (currentIteration != 0){
                double incDistance = 0.0;
                Coordinate newCoordinate = null;
                AgentState lastState;
                if (currentIteration != 0) {
                    lastState = agent.getLastState();
                    incDistance = agent.getSpeed() * (stepAmount/60);
                    newCoordinate = GeodeticCalculator.directProblem(lastState.getCoord(), incDistance, agent.getDirection());
                    agent.setCoordinate(newCoordinate);
                    agent.setDistanceFromStart(agent.getDistanceFromStart() + incDistance);
                }
            }
            Id<PolygonV2> polygonId = NetworkUtils.findPolygonByAgentCoords(this.network, agent.getCoordinate()).getId();
            agent.setPolygonId(polygonId);
        }
    }

    @Override
    public Fire getFire() {
        return fire;
    }

    @Override
    public Network getNetwork() { return network; }
}
