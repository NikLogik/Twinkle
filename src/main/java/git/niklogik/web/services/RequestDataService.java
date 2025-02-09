package git.niklogik.web.services;

import git.niklogik.db.services.CoordinateTransformationService;
import git.niklogik.web.models.CoordinateJson;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static git.niklogik.db.services.CoordinateTransformationService.TransformDirection.client_to_osmDB;

@Service
public class RequestDataService {

    private final Logger logger = LoggerFactory.getLogger(RequestDataService.class);

    private final CoordinateTransformationService transformationService;

    public RequestDataService(CoordinateTransformationService transformationService) {
        this.transformationService = transformationService;
    }

    public void transformCoordinates(List<CoordinateJson> coordinateList) {
        MathTransform mathTransformation = getTransformation();
        coordinateList.forEach(coordinate -> {
            try {
                coordinate.setCoordinate(JTS.transform(coordinate, null, mathTransformation));
            } catch (TransformException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    private MathTransform getTransformation() {
        return transformationService.getMathTransformation(client_to_osmDB);
    }
}
