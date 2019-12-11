package ru.nachos.core.controller.lib;

import org.springframework.stereotype.Component;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;

import java.util.Map;
import java.util.Set;

@Component
public interface Controller extends Runnable{

    InitialPreprocessingData getPreprocessingData();

    Config getConfig();

    Map<Id<Agent>, Agent> getAgentsForIter(int iterNum);

    Fire getFire();

    Network getNetwork();

    Map<Integer, Set<Id<Agent>>> getIterationMap();
}
