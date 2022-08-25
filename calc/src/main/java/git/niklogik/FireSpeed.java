package git.niklogik;

import git.niklogik.geo.Point3D;
import git.niklogik.speed.*;
import ru.nachos.core.network.lib.ForestFuelType;

public class FireSpeed {

    private final ForestFuelType fuelType;
    private final WindForecast windForecast;
    private final FreeFireSpeed freeFireSpeed;

    public FireSpeed(ForestFuelType fuelType, WindForecast windForecast) {
        this.fuelType = fuelType;
        this.windForecast = windForecast;
        this.freeFireSpeed = new FreeFireSpeed( fuelType );
    }

    public Double computeSpeed(Point3D start, Point3D end){
        return freeFireSpeed() * (1 + windRatio() + reliefRatio(start, end));
    }

    public Double computeDirectedSpeed(Point3D start, Point3D end, double direction){
        return computeSpeed(start, end) * directionRatio(direction);
    }

    private Double freeFireSpeed(){
        return freeFireSpeed.computeSpeed();
    }

    private Double reliefRatio(Point3D start, Point3D end){
        return new ReliefSpeedRatio(fuelType, new AngleTangent(start, end)).reliefRatio();
    }

    private Double windRatio(){
        return new WindSpeedRatio(fuelType, new AlbineWindSpeed(fuelType, windForecast.speed)).windRatio();
    }

    private Double directionRatio(Double direction){
        return new DirectionSpeedRatio(new AlbineWindSpeed(fuelType, windForecast.speed))
                .directionRatio(new DirectionDiff(windForecast.direction, direction));
    }
}
