package ru.nachos.core.network;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;

import java.util.Arrays;
import java.util.List;

class PolygonV2Impl extends PolygonV2 {

    private Id<PolygonV2> id;
    private int height;
    private PolygonType type;

    PolygonV2Impl(Id<PolygonV2> id){
        super(null, null, null);
        this.id = id;
    }

    PolygonV2Impl(Id<PolygonV2> id, LinearRing shell, LinearRing[] holes, GeometryFactory factory) {
        super(shell, holes, factory);
        this.id = id;
    }

    @Override
    public Id<PolygonV2> getId() {return this.id;}

    @Override
    public int getHeight() {return this.height;}

    @Override
    public void setHeight(int height) {this.height = height;}

    @Override
    public void setType(PolygonType typeName) { this.type = typeName; }

    @Override
    public PolygonType getPolygonType() { return this.type; }

    @Override
    public boolean addHole(LinearRing hole) {
        List<LinearRing> list = Arrays.asList(super.holes);
        boolean result = list.add(hole);
        super.holes = list.toArray(new LinearRing[list.size()]);
        return result;
    }
}
