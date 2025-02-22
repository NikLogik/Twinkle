package git.niklogik.web.services;

import git.niklogik.db.services.CoordinateTransformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.geotools.geometry.jts.JTS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import java.util.List;

import static git.niklogik.db.services.CoordinateTransformationService.TransformDirection.CLIENT_TO_OSM_DB;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestDataService {

    private final CoordinateTransformationService transformationService;

    public void transformCoordinates(List<Coordinate> coordinateList) {
        var mathTransformation = transformationService.getMathTransformation(CLIENT_TO_OSM_DB);
        coordinateList.forEach(coordinate -> {
            try {
                JTS.transform(coordinate, coordinate, mathTransformation);
            } catch (TransformException e) {
                log.error(e.getMessage(), e);
            }
        });
    }
}
