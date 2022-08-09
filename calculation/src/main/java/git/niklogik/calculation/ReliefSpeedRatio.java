package git.niklogik.calculation;

import git.niklogik.geo.Point3D;
import ru.nachos.core.network.lib.ForestFuelType;

public class ReliefSpeedRatio {
    private final Point3D start;
    private final Point3D end;
    private final ForestFuelType fuelType;
    private final Gamma gamma;

    public ReliefSpeedRatio(ForestFuelType fuelType, Point3D start, Point3D end) {
        this.start = start;
        this.end = end;
        this.fuelType = fuelType;
        this.gamma = new Gamma(fuelType);
    }

    public Double reliefRatio(){
        return 5.275 * Math.pow(betta(), -0.3) * Math.pow(tangentFi(), 2);
    }

    private Double betta(){
        return 8.858 * gamma.gamma() * Math.pow(fuelType.getSpecificArea(), -0.8189);
    }

    private Double tangentFi(){
        return end.dHeight(start) / start.distance(end);
    }
}
