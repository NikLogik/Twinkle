package git.niklogik.core.config;

import git.niklogik.core.config.lib.Config;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Value;

public class ConfigImpl implements Config {

    private String fireName;
    private int firstIteration;
    private int lastIteration;
    private int stepTimeAmount;
    private long startTime;
    private long endTime;
    private FireSpreadModels calculator;
    private int fuelType;
    private double windSpeed;
    private double windDirection;
    private double humidity;
    private double temperature;
    @Value("app.database.fires.srid")
    private int srid;
    private FirePowerClassification fireClass;
    private Coordinate fireCenterCoordinate;
    private int firePerimeter;
    private int fireAgentsDistance;

    ConfigImpl(){
        this.firstIteration = 1;
        this.calculator = FireSpreadModels.Rotermel;
        this.firePerimeter = 50; //for radius = 5 m
    }

    @Override
    public String getFireName() { return this.fireName; }

    @Override
    public int getFirstIteration() { return firstIteration; }

    @Override
    public int getLastIteration() { return lastIteration; }

    public void setLastIteration(int lastIteration) {
        this.lastIteration = lastIteration;
    }

    @Override
    public int getStepTimeAmount() { return stepTimeAmount; }

    public void setStepTimeAmount(int stepTimeAmount) {
        this.stepTimeAmount = stepTimeAmount;
    }

    @Override
    public long getStartTime() { return startTime; }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public long getEndTime() { return endTime; }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public FireSpreadModels getCalculator() { return calculator; }

    public void setCalculator(FireSpreadModels calculator) {
        this.calculator = calculator;
    }

    @Override
    public int getFuelType() { return fuelType; }

    public void setFuelType(int fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public double getWindSpeed() { return windSpeed; }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public double getWindDirection() { return windDirection; }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public double getTemperature() { return temperature; }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public double getHumidity() { return humidity; }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public FirePowerClassification getFireClass() { return fireClass; }

    public void setFireClass(FirePowerClassification fireClass) {
        this.fireClass = fireClass;
    }

    @Override
    public Coordinate getFireCenterCoordinate() { return fireCenterCoordinate; }

    public void setFireCenterCoordinate(Coordinate fireCenterCoordinate) {
        this.fireCenterCoordinate = fireCenterCoordinate;
    }

    @Override
    public int getFirePerimeter() { return firePerimeter; }

    public void setFirePerimeter(int firePerimeter) {
        this.firePerimeter = firePerimeter;
    }

    @Override
    public int getFireAgentsDistance() { return fireAgentsDistance; }

    public void setSrid(int srid) { this.srid = srid; }

    @Override
    public String getSrid() { return "EPSG:" + this.srid; }

    public void setFireAgentsDistance(int fireAgentsDistance) {
        this.fireAgentsDistance = fireAgentsDistance;
    }

    @Override
    public String toString() {
        return  "ConfigImpl{" + System.lineSeparator() +
                "firstIteration=" + firstIteration + System.lineSeparator() +
                ", lastIteration=" + lastIteration + System.lineSeparator() +
                ", stepTimeAmount=" + stepTimeAmount + System.lineSeparator() +
                ", startTime=" + startTime + System.lineSeparator() +
                ", endTime=" + endTime + System.lineSeparator() +
                ", calculator=" + calculator + System.lineSeparator() +
                ", fuelType=" + fuelType + System.lineSeparator() +
                ", windSpeed=" + windSpeed + System.lineSeparator() +
                ", windDirection=" + windDirection + System.lineSeparator() +
                ", humidity=" + humidity + System.lineSeparator() +
                ", fireClass=" + fireClass + System.lineSeparator() +
                ", fireCenterCoordinate=" + fireCenterCoordinate + System.lineSeparator() +
                ", firePerimeter=" + firePerimeter + System.lineSeparator() +
                ", fireAgentsDistance=" + fireAgentsDistance +
                "}";
    }
}
