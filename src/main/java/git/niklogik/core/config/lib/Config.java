package git.niklogik.core.config.lib;

import org.locationtech.jts.geom.Coordinate;
import org.springframework.stereotype.Component;

@Component
public interface Config {

    String getFireName();
    int getFirstIteration();
    int getLastIteration();
    int getStepTimeAmount();
    long getStartTime();
    long getEndTime();
    FireSpreadModels getCalculator();
    int getFuelType();
    double getWindSpeed();
    double getWindDirection();
    double getTemperature();
    double getHumidity();
    FirePowerClassification getFireClass();
    Coordinate getFireCenterCoordinate();
    int getFirePerimeter();
    int getFireAgentsDistance();
    String getSrid();

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
        FIRE_AGENTS_DISTANCE("fireAgentsDistance"),
        SRID("srid");

        private final String param;

        Definitions(String param){
            this.param = param;
        }

        public String getParam() {
            return param;
        }
    }

    enum FireSpreadModels{ Rotermel; }

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
