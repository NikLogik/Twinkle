package git.niklogik.web.services;

import org.geotools.geometry.jts.JTS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import git.niklogik.db.services.CoordinateTransformationService;

import java.util.List;

@SessionScope
@Service
public class RequestDataService {

    @Autowired
    CoordinateTransformationService transformationService;

    public void transformCoordinates(List<Coordinate> coordinateList) {
        MathTransform mathTransformation = transformationService.getMathTransformation(CoordinateTransformationService.TransformDirection.client_to_osmDB);
        coordinateList.forEach(coordinate -> {
            try {
                coordinate.setCoordinate(JTS.transform(coordinate, null, mathTransformation));
            } catch (TransformException e) {
                e.printStackTrace();
            }
        });
    }
}
