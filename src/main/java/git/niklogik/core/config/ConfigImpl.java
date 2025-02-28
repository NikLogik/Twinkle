package git.niklogik.core.config;

import git.niklogik.calc.speed.WindForecast;
import git.niklogik.core.config.lib.Config;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;

@Getter
@Setter
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

    @Override
    public WindForecast getForecast() {
        return new WindForecast(windSpeed.doubleValue(), windDirection);
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
