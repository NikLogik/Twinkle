package git.niklogik.speed;

import git.niklogik.geo.Point3D;
import ru.nachos.core.network.lib.ForestFuelType;

public class ReliefSpeedRatio {
    private final AngleTangent tangent;
    private final ForestFuelType fuelType;
    private final Gamma gamma;

    public ReliefSpeedRatio(ForestFuelType fuelType, AngleTangent tangent) {
        this.tangent = tangent;
        this.fuelType = fuelType;
        this.gamma = new Gamma(fuelType);
    }

    public Double reliefRatio(){
        return 5.275 * Math.pow(betta(), -0.3) * Math.pow(tangent.tangentFi(), 2);
    }

    private Double betta(){
        return 8.858 * gamma.gamma() * Math.pow(fuelType.getSpecificArea(), -0.8189);
    }
}
