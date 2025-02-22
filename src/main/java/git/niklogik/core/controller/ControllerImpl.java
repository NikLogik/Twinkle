package git.niklogik.core.controller;

import git.niklogik.core.Id;
import git.niklogik.core.controller.lib.Controller;
import git.niklogik.core.fire.FireModel;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentState;
import git.niklogik.core.fire.lib.AgentStatus;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.fire.lib.FireFactory;
import git.niklogik.core.replanning.EventManagerImpl;
import git.niklogik.core.replanning.EventsHandling;
import git.niklogik.core.replanning.events.AfterIterationEvent;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.core.controller.lib.IterationInfo;
import git.niklogik.core.fire.algorithms.FireSpreadCalculator;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.core.network.lib.Network;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.utils.AgentMap;
import git.niklogik.core.utils.GeodeticCalculator;
import git.niklogik.core.utils.PolygonType;
import git.niklogik.db.services.FireDatabaseService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;
import static git.niklogik.core.utils.GeodeticCalculator.directProblem;
import static java.math.RoundingMode.HALF_UP;

class ControllerImpl implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ControllerImpl.class);
    private final FireDatabaseService fireService;
    private final Network network;
    private final Config config;
    private final InitialPreprocessingData preprocessingData;
    private final Fire fire;
    private FireModel model;
    private final EventsHandling eventsHandler;
    private final Map<Integer, LinkedList<AgentState>> iterationMap = new TreeMap<>();
    public static final String DIVIDER = "###################################################";
    final String MARKER = "#####";
    private int currentIteration;
    private double currentTime;
    private final BigDecimal stepTimeAmount;

    ControllerImpl(InitialPreprocessingData preprocessing, FireDatabaseService fireService) {
        this.fireService = fireService;
        this.config = preprocessing.getConfig();
        this.preprocessingData = preprocessing;
        this.network = preprocessing.getNetwork();
        this.currentTime = config.getStartTime();
        this.stepTimeAmount = toBigDecimal(config.getStepTimeAmount());
        this.fire = preprocessing.getFire();
        this.eventsHandler = new EventsHandling(new EventManagerImpl());
    }

    @Override
    public void run() {
        Point point = new GeometryFactory().createPoint(config.getFireCenterCoordinate());
        point.setSRID(4326);
        this.model = fireService.createAndGetModel(fire, point, config.getLastIteration(), config.getFuelType());
        prepareAgentsToStart();
        doIteration();
    }

    private void prepareAgentsToStart() {
        logger.info(MARKER + "Preparing agents for start FIRE!!!");
        this.currentIteration = 0;
        for (Agent agent : this.fire.getTwinkles().values()) {
            Id<PolygonV2> polygonId = NetworkUtils.findPolygonByAgentCoords(this.network, agent.getCoordinate()).getId();
            agent.setPolygonId(polygonId);
            agent.setStatus(AgentStatus.ACTIVE);
            agent.saveState(currentIteration);
        }
        LinkedList<AgentState> listOfStates = FireUtils.getListOfStates(fire.getTwinkles(), currentIteration);
        iterationMap.put(currentIteration, listOfStates);
        Polygon iterPolygon = FireUtils.getPolygonFromAgentMap(fire.getTwinkles(), new GeometryFactory());
        fireService.saveIteration(currentIteration, iterPolygon, model);
    }

    private void doIteration() {
        logger.info(MARKER + "Start iterate");
        for (int start = config.getFirstIteration(); start < config.getLastIteration(); start++) {
            this.iteration(start);
        }
    }

    private void iteration(int iteration) {
        this.currentIteration = iteration;
        logger.info(DIVIDER + "Iteration #" + currentIteration + " begin");
        this.currentTime += stepTimeAmount.intValue();
        iterationStep();
        IterationInfo info = new IterationInfoImpl(currentIteration);
        eventsHandler.handleAfterIterationEnd(new AfterIterationEvent(currentIteration, info));
        eventsHandler.persistAndReset(iterationMap);
        Polygon iterPolygon = FireUtils.getPolygonFromAgentMap(fire.getTwinkles(), new GeometryFactory());
        fireService.saveIteration(currentIteration, iterPolygon, model);
        logger.info(DIVIDER + "Iteration #" + currentIteration + " finished");
    }

    private void iterationStep() {
        logger.info("Move agents to new locations");
        Iterator<Agent> iterator = fire.getTwinkles().iterator();
        while (iterator.hasNext()) {
            Agent agent = iterator.next();
            if (agent.getStatus().equals(AgentStatus.ACTIVE)) {
                Coordinate newCoordinate;
                AgentState lastState;
                lastState = agent.getLastState();
                if (network.getTrip(agent.getId()) != null) {
                    newCoordinate = GeodeticCalculator.distance(currentTime, network.getTrip(agent.getId()), agent);
                } else {
                    var distance = agent.getSpeed().multiply(stepTimeAmount.divide(toBigDecimal(60.0), HALF_UP));
                    newCoordinate = directProblem(lastState.getCoordinate(), distance, agent.getDirection());
                }
                agent.setCoordinate(newCoordinate);
                Id<PolygonV2> polygonId = NetworkUtils.findPolygonByAgentCoords(this.network, agent.getCoordinate()).getId();
                agent.setPolygonId(polygonId);
            }
        }
    }

    @Override
    public Fire getFire() {
        return fire;
    }

    @Override
    public Network getNetwork() {
        return network;
    }

    @Override
    public InitialPreprocessingData getPreprocessingData() {
        return this.preprocessingData;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public Map<Integer, LinkedList<AgentState>> getIterationMap() {
        return this.iterationMap;
    }

    @Override
    public FireModel getModel() {
        return model;
    }

    public class IterationInfoImpl implements IterationInfo {

        private final int iterNum;
        private final int iterStepTime;
        private final AgentMap agents;
        private final int agentDistance;
        private final FireSpreadCalculator calculator;
        private final FireFactory fireFactory;
        private final double fireSpeed;
        private final Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> polygons;
        private final double headDirection;
        private final Coordinate fireCenter;
        private final long simTime;
        private final double curTime;
        private final Network net;

        public IterationInfoImpl(int iterNum) {
            this.iterNum = iterNum;
            this.agents = fire.getTwinkles();
            this.agentDistance = config.getFireAgentsDistance();
            this.calculator = preprocessingData.getCalculator();
            this.fireFactory = fire.getFactory();
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
        public double getCurTime() {
            return curTime;
        }

        @Override
        public long getSimTime() {
            return simTime;
        }

        @Override
        public Network getNetwork() {
            return net;
        }

        @Override
        public AgentMap getAgents() {
            return agents;
        }

        @Override
        public double getFireSpeed() {
            return fireSpeed;
        }

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
        public Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> getPolygons() {
            return polygons;
        }

        @Override
        public int getIterStepTime() {
            return iterStepTime;
        }

        @Override
        public double getHeadDirection() {
            return headDirection;
        }

        @Override
        public Coordinate getFireCenter() {
            return fireCenter;
        }
    }
}
