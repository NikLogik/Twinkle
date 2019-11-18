package ru.nachos.core.network.lib;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasID;

import java.util.Map;

public interface Node extends HasID {
    @Override
    Id<Node> getId();

    boolean addInLink(Link link);

    boolean addOutLink(Link link);

    Link removeInLink(Id<Link> link);

    Link removeOutLink(Id<Link> link);

    Map<Id<Link>, Link> getInLinks();

    Map<Id<Link>, Link> getOutLinks();

    void setCoordinate(Coordinate coord);

    Coordinate getCoordinate();
}
