package git.niklogik.core.controller.lib;

import git.niklogik.core.fire.FireModel;
import git.niklogik.core.fire.lib.AgentState;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.config.lib.Config;
import git.niklogik.core.network.lib.Network;

import java.util.LinkedList;
import java.util.Map;

public interface Controller extends Runnable{

    InitialPreprocessingData getPreprocessingData();

    Config getConfig();

    Fire getFire();

    Network getNetwork();

    Map<Integer, LinkedList<AgentState>> getIterationMap();

    FireModel getModel();
}
