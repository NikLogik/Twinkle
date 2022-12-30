package git.niklogik.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Coordinate;
import git.niklogik.web.models.RequestDataImpl;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
