package git.niklogik.calculation;

import ru.nachos.core.network.lib.ForestFuelType;

public class ComputeFireSpeed {

    private final ForestFuelType fuelType;

    public ComputeFireSpeed(ForestFuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double computeFireSpread(WindForecast windForecast, Point3D start, Point3D end){
        FreeFireSpeed freeFireSpeed = new FreeFireSpeed(fuelType);

        return freeFireSpeed.computeSpeed()
                + new WindConstrainedFireSpeed(new WindSpeedRatio(fuelType, windForecast.speed), freeFireSpeed).computeSpeed()
                + new ReliefConstrainedFireSpeed(new ReliefSpeedRatio(fuelType, start, end), freeFireSpeed).computeSpeed();
    }

    public Double computeDirectedSpeed(WindForecast forecast, Point3D start, Point3D end){
        //TODO Завершить реализацию метода по расчету скорости с учетом направления
        throw new UnsupportedOperationException();
    }
}
