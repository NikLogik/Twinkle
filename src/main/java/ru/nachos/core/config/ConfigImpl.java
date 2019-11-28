package ru.nachos.core.config;

import com.vividsolutions.jts.geom.Coordinate;
import org.springframework.stereotype.Component;
import ru.nachos.core.config.lib.Config;

import java.util.Map;
import java.util.TreeMap;

import static ru.nachos.core.config.lib.Config.Definitions.*;

@Component
public final class ConfigImpl implements Config {

    private String fireName;
    private int firstIteration;
    private int lastIteration;
    private int stepTimeAmount;
    private double startTime;
    private double endTime;
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
        this.fireName = "default";
        params.put(FIRE_NAME.getParam(), fireName);
        this.firstIteration = 1;
        params.put(FIRST_ITERATION.getParam(),firstIteration);
        this.lastIteration = 11;
        params.put(LAST_ITERATION.getParam(), lastIteration);
        this.stepTimeAmount = 300;
        params.put(STEP_TIME_AMOUNT.getParam(), stepTimeAmount);
        this.startTime = 0;
        params.put(START_TIME.getParam(), startTime);
        this.endTime = 3600;
        params.put(END_TIME.getParam(), endTime);
        this.calculator = FireSpreadModels.Rotermel;
        params.put(ALGORITHM_TYPE.getParam(), calculator);
        this.fuelType = ForestFuelType.PineSparse;
        params.put(FUEL_TYPE_CODE.getParam(), fuelType);
        this.windSpeed = 4.6;
        params.put(WIND_SPEED.getParam(), windSpeed);
        this.windDirection = 45;
        params.put(WIND_DIRECTION.getParam(), windDirection);
        this.temperature = 17.5;
        params.put(TEMPERATURE.getParam(), temperature);
        this.humidity = 60;
        params.put(HUMIDITY.getParam(), humidity);
        this.fireClass = FirePowerClassification.Ignition;
        params.put(FIRE_CLASS.getParam(), fireClass);
        this.firePerimeter = 500; //for 20.000 square meters
        params.put(FIRE_PERIMETER.getParam(), firePerimeter);
        this.fireAgentsDistance = 10; //meters between agents
        params.put(FIRE_AGENTS_DISTANCE.getParam(), fireAgentsDistance);
        this.srid = "EPSG:4326";
        params.put(SRID.getParam(), srid);
        this.fireCenterCoordinate = new Coordinate(44.97385, 33.88063);
        params.put(FIRE_CENTER_COORDS.getParam(), fireCenterCoordinate);
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
    public double getStartTime() { return startTime; }

    public void setStartTime(double startTime) {
        updateParams(START_TIME.getParam(), startTime);
        this.startTime = startTime;
    }

    @Override
    public double getEndTime() { return endTime; }

    public void setEndTime(double endTime) {
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
        return  "firstIteration=" + firstIteration +
                ", lastIteration=" + lastIteration +
                ", stepTimeAmount=" + stepTimeAmount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", calculator=" + calculator +
                ", fuelType=" + fuelType +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", humidity=" + humidity +
                ", fireClass=" + fireClass +
                ", fireCenterCoordinate=" + fireCenterCoordinate +
                ", firePerimeter=" + firePerimeter +
                ", fireAgentsDistance=" + fireAgentsDistance;
    }
}
