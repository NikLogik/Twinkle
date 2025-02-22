package git.niklogik.error.exception;

import static java.lang.String.format;

public class FuelTypeNotFoundException extends NotFoundException {

    public FuelTypeNotFoundException(Integer fuelTypeId) {
        super(format("Fuel type not found with id: %s", fuelTypeId));
    }
}
