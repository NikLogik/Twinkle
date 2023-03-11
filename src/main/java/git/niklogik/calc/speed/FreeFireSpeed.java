package git.niklogik.calc.speed;

import git.niklogik.core.network.lib.ForestFuelType;

public class FreeFireSpeed implements ComputeFireSpeed {
    private final ForestFuelType fuelType;
    private final Gamma gamma;
    private final FireSpreadCharacteristics spreadCharacteristics;

    public FreeFireSpeed(ForestFuelType fuelType) {
        this.fuelType = fuelType;
        this.gamma = new Gamma(fuelType);
        this.spreadCharacteristics = new FireSpreadCharacteristics(fuelType);
    }

    public Double computeSpeed(){

        return 0.048 * fuelType.getHeat() * fuelType.getMineralConsistency() * fuelType.getMiddleReserve()
                * spreadCharacteristics.burningSlowdownRatio() * spreadCharacteristics.potentialSpeed() * spreadCharacteristics.heatStreamShare()
                / ((fuelType.getDensityForestFuel() + fuelType.getDensityForestFuel() * fuelType.getMineralMatter())
                * spreadCharacteristics.burningHeat() * gamma.gamma()
                * spreadCharacteristics.effectiveFuelDensity() * Math.pow(fuelType.getSpecificArea(), -0.8189));
    }
}
