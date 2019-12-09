package ru.nachos.core.network.lib;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasID;
import ru.nachos.core.utils.PolygonType;

public abstract class PolygonV2 extends Polygon implements HasID {

    @Override
    public abstract Id<PolygonV2> getId();

    public PolygonV2(LinearRing shell, LinearRing[] holes, GeometryFactory factory) {
        super(shell, holes, factory);
    }

    public abstract int getHeight();

    public abstract void setHeight(int height);

    public abstract void setType(PolygonType typeName);

    public abstract PolygonType getPolygonType();

    public abstract boolean addHole(LinearRing hole);
}
