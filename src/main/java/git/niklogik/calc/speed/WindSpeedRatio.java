package git.niklogik.calc.speed;

import git.niklogik.core.network.lib.ForestFuelType;

public class WindSpeedRatio {
    private final AlbineWindSpeed albineSpeed;
    private final ForestFuelType fuelType;
    private final Gamma gamma;

    public WindSpeedRatio(ForestFuelType fuelType, AlbineWindSpeed albineWindSpeed) {
        this.albineSpeed = albineWindSpeed;
        this.gamma = new Gamma(fuelType);
        this.fuelType = fuelType;
    }

    public Double windRatio(){
        double k1 = Math.pow(gamma.gamma(), -0.715 * Math.exp(-1.094 * Math.pow(10, -4) * fuelType.getSpecificArea()));
        double speed = albineSpeed.computeSpeed();
        double k2 = 7.47 * Math.exp((-0.06919 * Math.pow(fuelType.getSpecificArea(), 0.55))
                * Math.pow((196.848 * speed), 0.0133 * Math.pow(fuelType.getSpecificArea(), 0.54)));
        return k1 * k2;
    }
}
