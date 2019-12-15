package ru.nachos.core.controller.lib;

import org.springframework.stereotype.Component;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.utils.AgentMap;

import java.util.Map;

@Component
public interface Controller extends Runnable{

    InitialPreprocessingData getPreprocessingData();

    Config getConfig();

    AgentMap getAgentsForIter(int iterNum);

    Fire getFire();

    Network getNetwork();

    Map<Integer, AgentMap> getIterationMap();
}
