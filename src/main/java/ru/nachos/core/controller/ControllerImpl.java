package ru.nachos.core.controller;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.apache.log4j.Logger;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.controller.lib.IterationInfo;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.lib.*;
import ru.nachos.core.info.IterationInfoPrinter;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.replanning.EventManagerImpl;
import ru.nachos.core.replanning.EventsHandling;
import ru.nachos.core.replanning.events.AfterIterationEvent;
import ru.nachos.core.utils.AgentMap;
import ru.nachos.core.utils.GeodeticCalculator;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.model.fire.FireModel;
import ru.nachos.db.services.FireDatabaseService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

class ControllerImpl implements Controller {

    private static Logger logger = Logger.getLogger(ControllerImpl.class);
    private FireDatabaseService fireService;
    private Network network;
    private Config config;
    private InitialPreprocessingData preprocessingData;
    private Fire fire;
    private FireModel model;
    private EventsHandling eventsHandler;
    private Map<Integer, LinkedList<AgentState>> iterationMap = new TreeMap<>();
    public static final String DIVIDER = "###################################################";
    final String MARKER = "#####";
    private int currentIteration;
    private double currentTime;
    private double stepAmount;

    ControllerImpl(InitialPreprocessingData preprocessing, FireDatabaseService fireService){
        this.fireService = fireService;
        this.config = preprocessing.getConfig();
        this.preprocessingData = preprocessing;
        this.network = preprocessing.getNetwork();
        this.currentTime = config.getStartTime();
        this.stepAmount = config.getStepTimeAmount();
        this.fire = preprocessing.getFire();
        this.eventsHandler = new EventsHandling(new EventManagerImpl());
    }

    @Override
    public void run(){
        Point point = network.getFactory().getGeomFactory().createPoint(config.getFireCenterCoordinate());
        point.setSRID(4326);
        this.model = fireService.createAndGetFireModel(fire, point, config.getLastIteration());
        if (fireService == null){
            throw new NullPointerException("Fire Service is null");
        }
        prepareAgentsToStart();
        doIteration();
    }

    private void prepareAgentsToStart() {
        logger.info(MARKER + "Preparing agents for start FIRE!!!");
        this.currentIteration = 0;
        for(Agent agent : this.fire.getTwinkles().values()){
            Id<PolygonV2> polygonId = NetworkUtils.findPolygonByAgentCoords(this.network, agent.getCoordinate()).getId();
            agent.setPolygonId(polygonId);
            agent.setStatus(AgentStatus.ACTIVE);
            agent.saveState(currentIteration);
        }
        LinkedList<AgentState> listOfStates = FireUtils.getListOfStates(fire.getTwinkles(), currentIteration);
        iterationMap.put(currentIteration, listOfStates);
        Polygon iterPolygon = FireUtils.getPolygonFromAgentMap(fire.getTwinkles(), network.getFactory().getGeomFactory());
        fireService.saveIterationByFireId(currentIteration, iterPolygon, model);
    }

    private void doIteration(){
        logger.info(MARKER + "Start iterate");
        for (int start = config.getFirstIteration(); start < config.getLastIteration(); start++){
            this.iteration(start);
            IterationInfoPrinter.printResultData(start, iterationMap.get(start));
        }
    }

    private void iteration(int iteration) {
        this.currentIteration = iteration;
        logger.info(DIVIDER + "Iteration #" + currentIteration + " begin");
        this.currentTime += stepAmount;
        iterationStep();
        IterationInfo info = new IterationInfoImpl(currentIteration);
        eventsHandler.handleAfterIterationEnd(new AfterIterationEvent(currentIteration, info));
        eventsHandler.persistAndReset(iterationMap);
        Polygon iterPolygon = FireUtils.getPolygonFromAgentMap(fire.getTwinkles(), network.getFactory().getGeomFactory());
        fireService.saveIterationByFireId(currentIteration, iterPolygon, model);
        logger.info(DIVIDER + "Iteration #" + currentIteration + " finished");
    }

    private void iterationStep(){
        logger.info("Move agents to new locations");
        Iterator<Agent> iterator = fire.getTwinkles().iterator();
        while (iterator.hasNext()) {
            Agent agent = iterator.next();
            if (agent.getStatus().equals(AgentStatus.ACTIVE)) {
                double incDistance;
                Coordinate newCoordinate = null;
                AgentState lastState;
                lastState = agent.getLastState();
                if (network.getTrip(agent.getId()) != null){
                    newCoordinate = GeodeticCalculator.distance(currentTime, network.getTrip(agent.getId()), agent);
                } else {
                    incDistance = agent.getSpeed() * (stepAmount / 60);
                    newCoordinate = GeodeticCalculator.directProblem(lastState.getCoordinate(), incDistance, agent.getDirection());
                }
                agent.setCoordinate(newCoordinate);
                Id<PolygonV2> polygonId = NetworkUtils.findPolygonByAgentCoords(this.network, agent.getCoordinate()).getId();
                agent.setPolygonId(polygonId);
            }
        }
    }

    @Override
    public Fire getFire() { return fire; }

    @Override
    public Network getNetwork() { return network; }

    @Override
    public InitialPreprocessingData getPreprocessingData() { return this.preprocessingData; }

    @Override
    public Config getConfig() { return this.config; }

    @Override
    public Map<Integer, LinkedList<AgentState>> getIterationMap() {
        return this.iterationMap;
    }
    @Override
    public FireModel getModel() { return model; }

    public class IterationInfoImpl implements IterationInfo {

        private int iterNum;
        private int iterStepTime;
        private AgentMap agents;
        private int agentDistance;
        private FireSpreadCalculator calculator;
        private FireFactory fireFactory;
        private double fireSpeed;
        private GeometryFactory geomFactory;
        private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons;
        private double headDirection;
        private Coordinate fireCenter;
        private long simTime;
        private double curTime;
        private Network net;

        public IterationInfoImpl(int iterNum){
            this.iterNum = iterNum;
            //полуачем агентов, которые не умерли в конце предыдущей итерации для начала текущей
            this.agents = fire.getTwinkles();
            this.agentDistance = config.getFireAgentsDistance();
            this.calculator = preprocessingData.getCalculator();
            this.fireFactory = fire.getFactory();
            this.geomFactory = network.getFactory().getGeomFactory();
            this.polygons = network.getPolygones();
            this.fireSpeed = fire.getFireSpeed();
            this.iterStepTime = config.getStepTimeAmount();
            this.headDirection = fire.getHeadDirection();
            this.fireCenter = fire.getCenterPoint();
            this.simTime = config.getEndTime();
            this.curTime = currentTime;
            this.net = network;
        }
        @Override
        public double getCurTime() { return curTime; }
        @Override
        public long getSimTime() { return simTime; }
        @Override
        public Network getNetwork() { return net; }
        @Override
        public AgentMap getAgents() {
            return agents;
        }
        @Override
        public double getFireSpeed() { return fireSpeed; }
        @Override
        public int getIterNum() {
            return iterNum;
        }
        @Override
        public int getAgentDistance() {
            return agentDistance;
        }
        @Override
        public FireSpreadCalculator getCalculator() {
            return calculator;
        }
        @Override
        public FireFactory getFireFactory() {
            return fireFactory;
        }
        @Override
        public GeometryFactory getGeomFactory() {
            return geomFactory;
        }
        @Override
        public Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygons() {
            return polygons;
        }
        @Override
        public int getIterStepTime() { return iterStepTime; }
        @Override
        public double getHeadDirection() { return headDirection; }
        @Override
        public Coordinate getFireCenter() { return fireCenter; }
    }
}
