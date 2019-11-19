package ru.nachos.core.config.lib;

import com.vividsolutions.jts.geom.Coordinate;

import java.util.Map;

public interface Config {

    String getFireName();
    void addParam(String paramName, String value);
    Object getValue(String paramName);
    Map<String, Object> getParams();
    int getFirstIteration();
    int getLastIteration();
    int getStepTimeAmount();
    double getStartTime();
    double getEndTime();
    FireSpreadModels getCalculator();
    ForestFuelType getFuelType();
    double getWindSpeed();
    double getWindDirection();
    double getTemperature();
    double getHumidity();
    FirePowerClassification getFireClass();
    Coordinate getFireCenterCoordinate();
    int getFirePerimeter();
    int getFireAgentsDistance();

    enum Definitions{
        FIRE_NAME("fireName"),
        FIRST_ITERATION("startIteration"),
        LAST_ITERATION("lastIteration"),
        STEP_TIME_AMOUNT("stepTimeAmount"),
        START_TIME("startTime"),
        END_TIME("endTime"),
        ALGORITHM_TYPE("algorithm_type"),
        FUEL_TYPE_CODE("fuelTypeCode"),
        WIND_SPEED("windSpeed"),
        WIND_DIRECTION("windDirection"),
        TEMPERATURE("temperature"),
        HUMIDITY("humidity"),
        FIRE_CLASS("fireClass"),
        FIRE_CENTER_COORDS("fireCenterCoords"),
        FIRE_PERIMETER("firePerimeter"),
        FIRE_AGENTS_DISTANCE("fireAgentsDistance");

        private final String param;

        Definitions(String param){
            this.param = param;
        }

        public String getParam() {
            return param;
        }
    }

    enum FireSpreadModels{ Rotermel; }

    enum ForestFuelType {
        PineThick(802),
        PineSparse(303),
        SpruceThick(406),
        SpruceSparse(507),
        GreenwoodThick(710);

        private final int value;

        ForestFuelType(int value){ this.value = value; }

        public int getValue(){ return this.value; }

    }

    enum FirePowerClassification{
        Ignition(300),
        Small(1000),
        Middle(4000),
        Large(10000),
        Catastrophic(15000);

        private final int value;

        FirePowerClassification(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

    }
}