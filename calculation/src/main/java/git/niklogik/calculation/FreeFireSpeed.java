package git.niklogik.calculation;

import ru.nachos.core.network.lib.ForestFuelType;

public class FreeFireSpeed implements FireSpeed {
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
