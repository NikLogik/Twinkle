package git.niklogik.calculation;

import ru.nachos.core.network.lib.ForestFuelType;

public class WindSpeedRatio {
    private final AlbineWindSpeed albineSpeed;
    private final ForestFuelType fuelType;
    private final Gamma gamma;
    private final Double windSpeed;

    public WindSpeedRatio(ForestFuelType fuelType, Double windSpeed) {
        this.albineSpeed = new AlbineWindSpeed(fuelType);
        this.gamma = new Gamma(fuelType);
        this.fuelType = fuelType;
        this.windSpeed = windSpeed;
    }

    public Double windRatio(){
        double k1 = Math.pow(gamma.gamma(), -0.715 * Math.exp(-1.094 * Math.pow(10, -4) * fuelType.getSpecificArea()));
        double speed = albineSpeed.computeSpeed(windSpeed);
        double k2 = 7.47 * Math.exp((-0.06919 * Math.pow(fuelType.getSpecificArea(), 0.55))
                * Math.pow((196.848 * speed), 0.0133 * Math.pow(fuelType.getSpecificArea(), 0.54)));
        return k1 * k2;
    }
}
