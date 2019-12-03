package ru.nachos.web.models;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.web.models.lib.EstimateData;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class EstimateDataImpl implements EstimateData, Serializable {

    private double windSpeed;
    private double windDirection;
    private int fireClass;
    private CoordinateJson[] fireCenter;
    private int fuelTypeCode;
    private int iterationStepTime;
    private int lastIterationTime;
    private int fireAgentDistance;

    @Override
    public int getFireAgentDistance() { return fireAgentDistance; }

    @Override
    public double getWindSpeed() { return this.windSpeed; }

    @Override
    public double getWindDirection() {
        return this.windDirection;
    }

    @Override
    public int getFireClass() {
        return this.fireClass;
    }

    @Override
    public List<Coordinate> getFireCenter() { return Arrays.asList(this.fireCenter); }

    @Override
    public int getFuelTypeCode() { return this.fuelTypeCode; }

    @Override
    public int getIterationStepTime() {
        return this.iterationStepTime;
    }

    @Override
    public int getLastIterationTime() {
        return this.lastIterationTime;
    }

}
