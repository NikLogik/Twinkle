package git.niklogik.calculation;

import ru.nachos.core.network.lib.ForestFuelType;

public class AlbineWindSpeed {
    private final ForestFuelType fuelType;

    public AlbineWindSpeed(ForestFuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double computeSpeed(Double sourceSpeed){
        return 0.31 * speedOn6Meters(sourceSpeed) / (Math.sqrt(fuelType.getCapacityDensityWood() * fuelType.getTreeHeight())
                * Math.log((20 + 1.18 * fuelType.getTreeHeight()) / (0.43 * fuelType.getTreeHeight())));
    }

    private Double speedOn6Meters(Double sourceSpeed) {
        return sourceSpeed * Math.pow(((fuelType.getTreeHeight() + 6)/10.0), 0.28);
    }
}
