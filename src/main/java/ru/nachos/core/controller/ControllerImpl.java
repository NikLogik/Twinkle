package ru.nachos.core.controller;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.TwinkleUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.fire.lib.Fire;

import java.util.Map;

final class ControllerImpl implements Controller {

    private static Controller controller;

    private Config config;
    private InitialPreprocessingData preprocessingData;
    private Fire fire;
    public static final String DIVIDER = "###################################################";
    final String MARKER = "#####";
    private int currentIteration;
    private double currentTime;
    private double stepAmount;

    ControllerImpl(){}

    ControllerImpl(InitialPreprocessingData preprocessing){
        this.config = preprocessing.getConfig();
        this.preprocessingData = preprocessing;
        this.currentTime = config.getStartTime();
        this.stepAmount = config.getStepTimeAmount();
        this.fire = preprocessing.getFire();
    }

    @Override
    public InitialPreprocessingData getPreprocessingData() { return this.preprocessingData; }
    @Override
    public Config getConfig() { return this.config; }
    @Override
    public void run(){

    }

    private void doIteration(){
        for (int start = config.getFirstIteration(); start < config.getLastIteration(); start++){
            this.iteration(start);
        }
    }

    private void iteration(int iteration){
        this.currentIteration = iteration;
        long timeStart = System.currentTimeMillis();
        currentTime += stepAmount;
        iterationStep();
        long timePerIteration = System.currentTimeMillis() - timeStart;

    }

    private void iterationStep(){
        Map<Id<Agent>, Agent> agents = this.fire.getTwinkles();
        for (Agent agent : agents.values()){
            AgentState lastState = agent.getLastState();
            double incDistance = lastState.getDistanceFromStart() + (agent.getSpeed() * stepAmount);
            Coordinate coordinate = FireUtils.calculateCoordIncrement(lastState.getCoordinate(), incDistance, agent.getDirection());
            TwinkleUtils.setCoord(agent, coordinate);
        }
    }

    private void saveAgentState(Agent agent){

    }
}
