package git.niklogik.calc.speed;

import git.niklogik.core.network.lib.ForestFuelType;

public class FireSpreadCharacteristics {
    private final ForestFuelType fuelType;
    private final Gamma gamma;

    public FireSpreadCharacteristics(ForestFuelType fuelType) {
        this.fuelType = fuelType;
        this.gamma = new Gamma(fuelType);
    }

    public Double potentialSpeed() {
        return 0.168 * Math.pow(fuelType.getSpecificArea(), 1.5)
                * Math.pow((495 + 9.979 * Math.pow(10, -3) * Math.pow(fuelType.getSpecificArea(), 1.5)), -1)
                * Math.pow(Math.pow((4.239 * Math.pow(fuelType.getSpecificArea(), 0.1) - 7.27), -1), -1)
                * Math.exp(Math.pow((4.239 * Math.pow(fuelType.getSpecificArea(), 0.1) - 7.27), -1 * (1 - gamma.gamma())));
    }

    public Double heatStreamShare(){
        return Math.pow((192 + 0.079 * fuelType.getSpecificArea()), -1)
                * Math.exp((0.792 + 0.376 * Math.pow(fuelType.getSpecificArea(), 0.5))
                * (gamma.gamma() * 8.858 * Math.pow(fuelType.getSpecificArea(), -0.8189) + 0.1));
    }

    public Double effectiveFuelDensity() {
        return Math.pow(Math.E, -452.759 / fuelType.getSpecificArea());
    }

    public Double burningSlowdownRatio() {
        return (1 - 2.59 * fuelType.getMoisture()) / fuelType.getMaxMoisture()
                + 5.11 * Math.pow((fuelType.getMoisture() / fuelType.getMaxMoisture()), 2)
                - 3.52 * Math.pow((fuelType.getMoisture() / fuelType.getMaxMoisture()), 3);
    }

    public Double burningHeat(){
        return 250 + 1116 * fuelType.getMoisture();
    }
}
