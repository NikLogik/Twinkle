package ru.nachos.web.services;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.nachos.db.services.CoordinateTransformationService;
import ru.nachos.web.models.lib.RequestData;

import java.util.List;

@SessionScope
@Service
public class RequestDataService {

    @Autowired
    CoordinateTransformationService transformationService;

    public boolean validateData(RequestData requestData) {
        if (requestData.getFireCenter() == null || requestData.getFireCenter().size() < 1){
            return false;
        }
        if (requestData.getFireClass() == 0 || requestData.getFireClass() > 5){
            return false;
        }
        if (requestData.getIterationStepTime() == 0){
            return false;
        }
        if (requestData.getLastIterationTime() == 0){
            return false;
        }
        if (requestData.getWindSpeed() == 0){
            return false;
        }
        transformCoordinates(requestData.getFireCenter());
        return true;
    }

    private void transformCoordinates(List<Coordinate> coordinateList){
        MathTransform mathTransformation = transformationService.getMathTransformation(CoordinateTransformationService.TransformDirection.client_to_osmDB);
        coordinateList.forEach(coordinate-> {
            try {
                coordinate.setCoordinate(JTS.transform(coordinate, null, mathTransformation));
            } catch (TransformException e) {
                e.printStackTrace();
            }
        });
    }
}
