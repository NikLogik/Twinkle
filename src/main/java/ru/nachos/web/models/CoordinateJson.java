package ru.nachos.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vividsolutions.jts.geom.Coordinate;

@JsonDeserialize
public class CoordinateJson extends Coordinate {

    private double x;
    private double y;
    @JsonIgnore
    private double z;

    public CoordinateJson(double x, double y) {
        super(x, y);
    }

    public CoordinateJson(Coordinate coordinate){
        this(coordinate.x, coordinate.y);
    }

    public double getX() {
        return super.x;
    }

    public void setX(double x) {
        super.x = x;
    }

    public double getY() {
        return super.y;
    }

    public void setY(double y) {
        super.y = y;
    }
}
