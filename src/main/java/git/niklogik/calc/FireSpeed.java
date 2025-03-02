package git.niklogik.calc;

import git.niklogik.calc.geo.Point3D;
import git.niklogik.calc.speed.AlbineWindSpeed;
import git.niklogik.calc.speed.AngleTangent;
import git.niklogik.calc.speed.DirectionDiff;
import git.niklogik.calc.speed.DirectionSpeedRatio;
import git.niklogik.calc.speed.FreeFireSpeed;
import git.niklogik.calc.speed.ReliefSpeedRatio;
import git.niklogik.calc.speed.WindForecast;
import git.niklogik.calc.speed.WindSpeedRatio;
import git.niklogik.core.network.lib.ForestFuelType;

public class FireSpeed {

    private final WindForecast windForecast;
    private final FreeFireSpeed freeFireSpeed;
    private final ReliefSpeedRatio reliefSpeedRatio;
    private final WindSpeedRatio windSpeedRatio;
    private final DirectionSpeedRatio directionSpeedRatio;

    public FireSpeed(ForestFuelType fuelType, WindForecast windForecast) {
        this.windForecast = windForecast;
        this.reliefSpeedRatio = new ReliefSpeedRatio(fuelType);
        var albineWindSpeed = new AlbineWindSpeed(fuelType, windForecast.speed());
        this.directionSpeedRatio = new DirectionSpeedRatio(albineWindSpeed);
        this.windSpeedRatio = new WindSpeedRatio(fuelType, albineWindSpeed);
        this.freeFireSpeed = new FreeFireSpeed(fuelType);
    }

    public Double computeSpeed(Point3D start, Point3D end) {
        return freeFireSpeed() * (1 + windRatio() + reliefRatio(start, end));
    }

    public Double computeDirectedSpeed(Point3D start, Point3D end, double direction) {
        return computeSpeed(start, end) * directionRatio(direction);
    }

    private Double freeFireSpeed() {
        return freeFireSpeed.computeSpeed();
    }

    private Double reliefRatio(Point3D start, Point3D end) {
        return reliefSpeedRatio.reliefRatio(new AngleTangent(start, end));
    }

    private Double windRatio() {
        return windSpeedRatio.windRatio();
    }

    private Double directionRatio(Double direction) {
        return directionSpeedRatio.directionRatio(new DirectionDiff(windForecast.direction(), direction));
    }
}
