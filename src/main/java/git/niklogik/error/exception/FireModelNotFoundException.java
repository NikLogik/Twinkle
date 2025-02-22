package git.niklogik.error.exception;

import static java.lang.String.format;

public class FireModelNotFoundException extends NotFoundException {

    public FireModelNotFoundException(Long fireModelId) {
        super(format("Fire not found with id: %s", fireModelId));
    }
}
