package git.niklogik.calc.speed;

import git.niklogik.core.network.lib.ForestFuelType;

public class AlbineWindSpeed {
    private final ForestFuelType fuelType;
    private final Double sourceSpeed;

    public AlbineWindSpeed(ForestFuelType fuelType, Double windSpeed) {
        this.fuelType = fuelType;
        this.sourceSpeed = windSpeed;
    }

    public Double computeSpeed(){
        return 0.31 * speedOn6Meters() / (Math.sqrt(fuelType.getCapacityDensityWood() * fuelType.getTreeHeight())
                * Math.log((20 + 1.18 * fuelType.getTreeHeight()) / (0.43 * fuelType.getTreeHeight())));
    }

    private Double speedOn6Meters() {
        return sourceSpeed * Math.pow(((fuelType.getTreeHeight() + 6)/10.0), 0.28);
    }
}
