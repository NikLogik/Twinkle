package ru.nachos.core.replanning.events;

import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.replanning.Event;

public class AgentsChangePolygonEvent extends Event {

    private Network network;
    private Fire fire;

    public AgentsChangePolygonEvent(int iterNum, Network network, Fire fire) {
        super(iterNum);
        this.network = network;
        this.fire = fire;
    }
    @Override
    public String getEventType() {
        return "stopped";
    }

    public Fire getFire() { return fire; }

    public Network getNetwork() { return network; }

}
