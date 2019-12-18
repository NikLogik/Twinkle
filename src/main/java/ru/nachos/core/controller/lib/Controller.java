package ru.nachos.core.controller.lib;

import org.springframework.stereotype.Component;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;

import java.util.LinkedList;
import java.util.Map;

@Component
public interface Controller extends Runnable{

    InitialPreprocessingData getPreprocessingData();

    Config getConfig();

    Fire getFire();

    Network getNetwork();

    Map<Integer, LinkedList<AgentState>> getIterationMap();
}
