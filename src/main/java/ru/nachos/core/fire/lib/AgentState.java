package ru.nachos.core.fire.lib;

import com.vividsolutions.jts.geom.Point;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.PolygonV2;

public interface AgentState {

    Agent getAgent();

    int getDistanceFromStart();

    void setDistanceFromStart(int distance);

    Point getPoint();

    void setTimeStamp(long timeStamp);

    long getTimeStamp();

    void setItersNumber(int itersNumber);

    int getItersNumber();

    void setIdPolygon(Id<PolygonV2> id);

    Id<PolygonV2> getIdPolygon();

    void setAgent(Agent twinkle);
}
