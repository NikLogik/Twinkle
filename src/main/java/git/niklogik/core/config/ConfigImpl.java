package git.niklogik.core.config;

import git.niklogik.calc.speed.WindForecast;
import git.niklogik.core.config.lib.Config;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;

@Getter
public class ConfigImpl implements Config {

    private String fireName;
    private int firstIteration;
    private int lastIteration;
    private int stepTimeAmount;
    private long startTime;
    private long endTime;
    private FireSpreadModels calculator;
    private int fuelType;
    private BigDecimal windSpeed;
    private double windDirection;
    private double humidity;
    private double temperature;
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

    public void setLastIteration(int lastIteration) {
        this.lastIteration = lastIteration;
    }

    public void setStepTimeAmount(int stepTimeAmount) {
        this.stepTimeAmount = stepTimeAmount;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setCalculator(FireSpreadModels calculator) {
        this.calculator = calculator;
    }

    public void setFuelType(int fuelType) {
        this.fuelType = fuelType;
    }

    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setFireClass(FirePowerClassification fireClass) {
        this.fireClass = fireClass;
    }

    public void setFireCenterCoordinate(Coordinate fireCenterCoordinate) {
        this.fireCenterCoordinate = fireCenterCoordinate;
    }

    public void setFirePerimeter(int firePerimeter) {
        this.firePerimeter = firePerimeter;
    }

    public void setSrid(int srid) { this.srid = srid; }

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
