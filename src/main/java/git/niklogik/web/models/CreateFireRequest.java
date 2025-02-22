package git.niklogik.web.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public record CreateFireRequest(
        @Positive
        Double windSpeed,
        @NotNull
        Double windDirection,
        @Min(1) @Max(4)
        Integer fireClass,
        @NotNull
        @Size(min = 1)
        List<Coordinate> fireCenter,
        @NotNull
        Integer fuelTypeCode,
        @Positive
        Integer iterationStepTime,
        @Positive
        Integer lastIterationTime,
        @Positive
        Integer fireAgentDistance) {
}
