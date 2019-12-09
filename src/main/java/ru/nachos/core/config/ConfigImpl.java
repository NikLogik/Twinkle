package ru.nachos.core.config;

import com.vividsolutions.jts.geom.Coordinate;
import org.springframework.stereotype.Component;
import ru.nachos.core.config.lib.Config;

import java.util.Map;
import java.util.TreeMap;

import static ru.nachos.core.config.lib.Config.Definitions.*;

@Component
public class ConfigImpl implements Config {

    private String fireName;
    private int firstIteration;
    private int lastIteration;
    private int stepTimeAmount;
    private long startTime;
    private long endTime;
    private FireSpreadModels calculator;
    private ForestFuelType fuelType;
    private double windSpeed;
    private double windDirection;
    private double humidity;
    private double temperature;
    private String srid;
    private FirePowerClassification fireClass;
    private Coordinate fireCenterCoordinate;
    private int firePerimeter;
    private int fireAgentsDistance;
    private TreeMap<String, Object> params = new TreeMap<>();
    ConfigImpl(){
//        this.fireName = "default";
        this.firstIteration = 0;
//        this.lastIteration = 11;
//        this.stepTimeAmount = 300;
//        this.startTime = 0;
//        this.endTime = 3600;
        this.calculator = FireSpreadModels.Rotermel;
//        this.fuelType = ForestFuelType.PineSparse;
//        this.windSpeed = 4.6;
//        this.windDirection = 45;
//        this.temperature = 17.5;
//        this.humidity = 60;
//        this.fireClass = FirePowerClassification.Ignition;
        this.firePerimeter = 500; //for 20.000 square meters
//        this.fireAgentsDistance = 10; //meters between agents
        this.srid = "EPSG:4326";
//        this.fireCenterCoordinate = new Coordinate(44.97385, 33.88063);
    }

    @Override
    public String getFireName() { return this.fireName; }

    public void setFireName(String fireName) {
        updateParams(FIRE_NAME.getParam(), fireName);
        this.fireName = fireName;
    }

    @Override
    public void addParam(String paramName, String value) { this.params.put(paramName, value); }

    @Override
    public Object getValue(String paramName) {
        return params.get(paramName);
    }

    @Override
    public Map<String, Object> getParams() {
        return this.params;
    }

    private void updateParams(String paramName, Object newParam){
        this.params.put(paramName, newParam);
    }

    @Override
    public int getFirstIteration() { return firstIteration; }

    void setFirstIteration(int firstIteration) {
        updateParams(FIRST_ITERATION.getParam(), firstIteration);
        this.firstIteration = firstIteration;
    }

    @Override
    public int getLastIteration() { return lastIteration; }

    public void setLastIteration(int lastIteration) {
        updateParams(LAST_ITERATION.getParam(), lastIteration);
        this.lastIteration = lastIteration;
    }

    @Override
    public int getStepTimeAmount() { return stepTimeAmount; }

    public void setStepTimeAmount(int stepTimeAmount) {
        updateParams(STEP_TIME_AMOUNT.getParam(), stepTimeAmount);
        this.stepTimeAmount = stepTimeAmount;
    }

    @Override
    public long getStartTime() { return startTime; }

    public void setStartTime(long startTime) {
        updateParams(START_TIME.getParam(), startTime);
        this.startTime = startTime;
    }

    @Override
    public long getEndTime() { return endTime; }

    public void setEndTime(long endTime) {
        updateParams(END_TIME.getParam(), endTime);
        this.endTime = endTime;
    }

    @Override
    public FireSpreadModels getCalculator() { return calculator; }

    public void setCalculator(FireSpreadModels calculator) {
        updateParams(ALGORITHM_TYPE.getParam(), calculator);
        this.calculator = calculator;
    }

    @Override
    public ForestFuelType getFuelType() { return fuelType; }

    public void setFuelType(ForestFuelType fuelType) {
        updateParams(FUEL_TYPE_CODE.getParam(), fuelType);
        this.fuelType = fuelType;
    }

    @Override
    public double getWindSpeed() { return windSpeed; }

    public void setWindSpeed(double windSpeed) {
        updateParams(WIND_SPEED.getParam(), windSpeed);
        this.windSpeed = windSpeed;
    }

    @Override
    public double getWindDirection() { return windDirection; }

    public void setWindDirection(double windDirection) {
        updateParams(WIND_DIRECTION.getParam(), windDirection);
        this.windDirection = windDirection;
    }

    @Override
    public double getTemperature() { return temperature; }

    public void setTemperature(double temperature) {
        updateParams(TEMPERATURE.getParam(), temperature);
        this.temperature = temperature;
    }

    @Override
    public double getHumidity() { return humidity; }

    public void setHumidity(double humidity) {
        updateParams(HUMIDITY.getParam(), humidity);
        this.humidity = humidity;
    }

    @Override
    public FirePowerClassification getFireClass() { return fireClass; }

    public void setFireClass(FirePowerClassification fireClass) {
        updateParams(FIRE_CLASS.getParam(), fireClass);
        this.fireClass = fireClass;
    }

    @Override
    public Coordinate getFireCenterCoordinate() { return fireCenterCoordinate; }

    public void setFireCenterCoordinate(Coordinate fireCenterCoordinate) {
        updateParams(FIRE_CENTER_COORDS.getParam(), fireCenterCoordinate);
        this.fireCenterCoordinate = fireCenterCoordinate;
    }

    @Override
    public int getFirePerimeter() { return firePerimeter; }

    public void setFirePerimeter(int firePerimeter) {
        updateParams(FIRE_PERIMETER.getParam(), firePerimeter);
        this.firePerimeter = firePerimeter;
    }

    @Override
    public int getFireAgentsDistance() { return fireAgentsDistance; }

    @Override
    public String getSrid() {
        return this.srid;
    }

    public void setFireAgentsDistance(int fireAgentsDistance) {
        updateParams(FIRE_AGENTS_DISTANCE.getParam(), fireAgentsDistance);
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
