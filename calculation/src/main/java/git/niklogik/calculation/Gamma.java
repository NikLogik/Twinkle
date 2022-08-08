package git.niklogik.calculation;

import ru.nachos.core.network.lib.ForestFuelType;

public class Gamma {
    private final ForestFuelType fuelType;

    public Gamma(ForestFuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double gamma(){
        return (3.767 * (Math.pow(10, -4)) * fuelType.getMiddleReserve())
                / (fuelType.getDepth() * Math.pow(fuelType.getSpecificArea(), -0.8189));
    }
}
