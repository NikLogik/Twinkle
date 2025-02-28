package git.niklogik.core.fire.algorithms;

import git.niklogik.calc.geo.Point3D;
import git.niklogik.calc.speed.AlbineWindSpeed;
import git.niklogik.calc.speed.AngleTangent;
import git.niklogik.calc.speed.DirectionDiff;
import git.niklogik.calc.speed.DirectionSpeedRatio;
import git.niklogik.calc.speed.FreeFireSpeed;
import git.niklogik.calc.speed.ReliefSpeedRatio;
import git.niklogik.calc.speed.WindSpeedRatio;
import git.niklogik.core.network.lib.ForestFuelType;
import git.niklogik.core.network.lib.Node;
import git.niklogik.core.weather.Weather;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;

@RequiredArgsConstructor
public class Rotermel implements FireSpreadCalculator {

    @Setter private ForestFuelType type;
    private final Weather weather;

    @Override
    public BigDecimal calculateForDirection(BigDecimal freeFireSpeed, BigDecimal agentDirection) {
        Double directionRatio = new DirectionSpeedRatio(
            new AlbineWindSpeed(type, weather.windSpeed())).directionRatio(
            new DirectionDiff(weather.windDirection(), agentDirection.doubleValue())
        );
        return toBigDecimal(freeFireSpeed.doubleValue() * directionRatio);
    }

    @Override
    public BigDecimal freeFireSpeed(ForestFuelTypeDao fuelType, BigDecimal windSpeed) {
        double speed = new FreeFireSpeed(fuelType).computeSpeed();
        double windK = new WindSpeedRatio(fuelType, new AlbineWindSpeed(fuelType, windSpeed.doubleValue())).windRatio();
        return toBigDecimal(speed * (1 + windK));
    }

    @Override
    public BigDecimal reliefCoefficient(Node from, Node to) {
        var fromPoint = new Point3D(from.getElevation(), from.getCoordinate());
        var toPoint = new Point3D(to.getElevation(), to.getCoordinate());
        return toBigDecimal(
            new ReliefSpeedRatio(type)
                .reliefRatio(new AngleTangent(fromPoint, toPoint))
        );
    }
}
