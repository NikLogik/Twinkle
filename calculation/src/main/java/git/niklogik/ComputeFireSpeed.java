package git.niklogik;

import git.niklogik.calculation.*;
import git.niklogik.geo.Point3D;
import ru.nachos.core.network.lib.ForestFuelType;

public class ComputeFireSpeed {

    private final ForestFuelType fuelType;
    private final WindForecast windForecast;

    public ComputeFireSpeed(ForestFuelType fuelType, WindForecast windForecast) {
        this.fuelType = fuelType;
        this.windForecast = windForecast;
    }

    public Double computeFireSpread(Point3D start, Point3D end){
        FreeFireSpeed freeFireSpeed = new FreeFireSpeed(fuelType);

        return freeFireSpeed.computeSpeed()
                + new WindConstrainedFireSpeed(new WindSpeedRatio(fuelType, windForecast.speed), freeFireSpeed).computeSpeed()
                + new ReliefConstrainedFireSpeed(new ReliefSpeedRatio(fuelType, start, end), freeFireSpeed).computeSpeed();
    }

    public Double computeDirectedSpeed(Point3D start, Point3D end, double direction){
        //TODO Завершить реализацию метода по расчету скорости с учетом направления
        Double diffDirection = new DirectionDiff(windForecast.direction, direction).difference();
        throw new UnsupportedOperationException();
    }
}
