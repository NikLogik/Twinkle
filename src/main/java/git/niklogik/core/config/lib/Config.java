package git.niklogik.core.config.lib;

import git.niklogik.calc.speed.WindForecast;
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

    WindForecast getForecast();

    double getTemperature();
    double getHumidity();
    FirePowerClassification getFireClass();
    Coordinate getFireCenterCoordinate();
    int getFirePerimeter();
    int getFireAgentsDistance();
    String getSrid();

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
