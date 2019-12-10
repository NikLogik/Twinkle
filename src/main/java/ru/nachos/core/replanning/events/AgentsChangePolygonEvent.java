package ru.nachos.core.replanning.events;

import ru.nachos.core.controller.lib.Controller;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.replanning.Event;

public class AgentsChangePolygonEvent extends Event {

    private Network network;
    private Fire fire;
    private Controller controller;

    public AgentsChangePolygonEvent(int iterNum, Controller controller) {
        super(iterNum);
        this.network = controller.getNetwork();
        this.fire = controller.getFire();
        this.controller = controller;
    }

    @Override
    public String getEventType() {
        return "stopped";
    }
    public Fire getFire() { return fire; }

    public Network getNetwork() { return network; }

    public Controller getController() { return controller; }
}
