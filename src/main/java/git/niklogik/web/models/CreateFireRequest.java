package git.niklogik.web.models;

import jakarta.validation.constraints.*;

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
        List<CoordinateJson> fireCenter,
        @NotNull
        Integer fuelTypeCode,
        @Positive
        Integer iterationStepTime,
        @Positive
        Integer lastIterationTime,
        @Positive
        Integer fireAgentDistance) {
}
