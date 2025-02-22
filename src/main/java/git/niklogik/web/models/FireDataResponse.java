package git.niklogik.web.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;

@JsonDeserialize
@JsonSerialize
public record FireDataResponse(
        Long fireId,
        Integer iterNum,
        Integer currentIterNum,
        List<Coordinate> coordinates) {
}
