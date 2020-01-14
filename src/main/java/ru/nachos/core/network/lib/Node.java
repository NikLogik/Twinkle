package ru.nachos.core.network.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasID;

public interface Node extends HasID {
    @Override
    Id<Node> getId();

    double getElevation();

    void setInLink(Link link);

    void setOutLink(Link link);

    Link getInLink();

    Link getOutLink();

    void setCoordinate(Coordinate coordinate);

    Coordinate getCoordinate();

    void setTripTime(int time);

    int getTripTime();
}
