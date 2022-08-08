package git.niklogik.calculation;

import ru.nachos.core.network.lib.ForestFuelType;

public class FreeFireSpeed implements FireSpeed {
    private final ForestFuelType fuelType;
    private final Gamma gamma;

    public FreeFireSpeed(ForestFuelType fuelType) {
        this.fuelType = fuelType;
        this.gamma = new Gamma(fuelType);
    }

    public Double computeSpeed(){
        FireSpreadCharacteristics freeFireSpeed = new FireSpreadCharacteristics(fuelType);

        return 0.048 * fuelType.getHeat() * fuelType.getMineralConsistency() * fuelType.getMiddleReserve()
                * freeFireSpeed.burningSlowdownRatio() * freeFireSpeed.potentialSpeed() * freeFireSpeed.heatStreamShare()
                / ((fuelType.getDensityForestFuel() + fuelType.getDensityForestFuel() * fuelType.getMineralMatter())
                * freeFireSpeed.burningHeat() * gamma.gamma()
                * freeFireSpeed.effectiveFuelDensity() * Math.pow(fuelType.getSpecificArea(), -0.8189));
    }
}
