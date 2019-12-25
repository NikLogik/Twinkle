package ru.nachos.core.network;

import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasElevation;
import ru.nachos.core.controller.lib.HasID;

public class ContourLine extends LineString implements HasID, HasElevation {

    private Id<ContourLine> id;
    private double elevation;

    public ContourLine(long id, double elevation, LineString lineString){
        this(lineString.getCoordinateSequence(), lineString.getFactory());
        this.id = Id.create(id, ContourLine.class);
    }

    public ContourLine(CoordinateSequence points, GeometryFactory factory) {
        super(points, factory);
    }

    @Override
    public double getElevation() {
        return this.elevation;
    }

    @Override
    public Id<ContourLine> getId() {
        return this.id;
    }
}
