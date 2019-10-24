package ru.nachos.core.fire.lib;

import ru.nachos.core.Coord;

public interface AgentPlan {

    Agent getAgent();

    void setAgent(Agent agent);

    int getDistanceFromStart();

    void setDistanceFromStart(int distance);

    Coord getCoord();

    void setCoord(Coord coord);

    void setTimeStamp(long timeStamp);

    long getTimeStamp();

    void setItersNumber(int itersNumber);

    int getItersNumber();
}
