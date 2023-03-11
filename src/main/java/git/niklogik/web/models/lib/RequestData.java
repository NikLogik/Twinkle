package git.niklogik.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Coordinate;
import git.niklogik.web.models.RequestDataImpl;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

@JsonDeserialize(as = RequestDataImpl.class)
@JsonSerialize(as = RequestDataImpl.class)
public interface RequestData {

    @Positive
    Integer getFireAgentDistance();

    @Positive
    Double getWindSpeed();

    @NotNull
    Double getWindDirection();

    @Min(1) @Max(4)
    int getFireClass();

    @NotNull
    @Size(min = 1)
    List<Coordinate> getFireCenter();

    @NotNull
    Integer getFuelTypeCode();

    @Positive
    Integer getIterationStepTime();

    @Positive
    Integer getLastIterationTime();
}
