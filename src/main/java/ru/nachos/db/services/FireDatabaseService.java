package ru.nachos.db.services;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.db.model.fire.FireFrontModel;
import ru.nachos.db.model.fire.FireModel;
import ru.nachos.db.model.fire.ForestFuelType;
import ru.nachos.db.repository.fire.FireFrontModelRepository;
import ru.nachos.db.repository.fire.FireModelRepository;
import ru.nachos.db.repository.fire.ForestFuelTypeRepository;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseDataImpl;
import ru.nachos.web.models.lib.ResponseData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FireDatabaseService {

    private FireModelRepository fireModelRepository;
    private FireFrontModelRepository fireFrontModelRepository;
    private ForestFuelTypeRepository forestRepository;
    private CoordinateTransformationService transformationService;

    @Autowired
    public FireDatabaseService(FireModelRepository fireModelRepository, FireFrontModelRepository fireFrontModelRepository, CoordinateTransformationService transformationService
    , ForestFuelTypeRepository forestRepository){
        this.fireModelRepository = fireModelRepository;
        this.fireFrontModelRepository = fireFrontModelRepository;
        this.transformationService = transformationService;
        this.forestRepository = forestRepository;
    }

    public FireModel createAndGetFireModel(Fire fire, Point center, int iterAmount){
        FireModel model = new FireModel(fire.getHeadDirection(), fire.getFireSpeed(),  fire.getFireClass(), iterAmount, center);
        return fireModelRepository.save(model);
    }

    public boolean saveIterationByFireId(int currentIteration, Polygon front, FireModel model){
        MathTransform mathTransformation = transformationService.getMathTransformation(CoordinateTransformationService.TransformDirection.osmDB_to_fireDB);
        Coordinate[] coordinates = front.getCoordinates();
        try{
            for (int i=0; i<coordinates.length; i++){
                coordinates[i].setCoordinate(JTS.transform(coordinates[i], null, mathTransformation));
            }
        } catch (TransformException ex){
            ex.printStackTrace();
        }
        front.setSRID(transformationService.getFireDatabaseSRID());
        FireFrontModel frontModel = new FireFrontModel(model.getIterations_num(), currentIteration, front, model);
        FireFrontModel save = fireFrontModelRepository.save(frontModel);
        return save != null;
    }

    public ResponseData getResponseDataByFireIdAndIterNumber(long fireId, int iterNumber){
        FireFrontModel fireFrontModel = fireFrontModelRepository.findFireFrontModelByFire_FireIdAndIterNumber(fireId, iterNumber);
        Geometry polygon = fireFrontModel.getPolygon();
        CoordinateJson[] collect = Arrays.stream(((Polygon) polygon).getExteriorRing().getCoordinates()).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).toArray(CoordinateJson[]::new);
        return new ResponseDataImpl(fireId, fireFrontModel.getIterAmount(), fireFrontModel.getIterNumber(), collect);
    }

    public ResponseData getResponseDataByFireIdWhereIterNumberIsMin(long fireId){
        FireFrontModel model = fireFrontModelRepository.findFireFrontModelByFire_FireIdWithMinIterNumber(fireId);
        Geometry polygon = model.getPolygon();
        CoordinateJson[] collect = Arrays.stream(((Polygon) polygon).getExteriorRing().getCoordinates()).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).toArray(CoordinateJson[]::new);
        return new ResponseDataImpl(fireId, model.getIterAmount(), model.getIterNumber(), collect);
    }

    public void deleteFireModelByFireId(long fireId){
        fireFrontModelRepository.deleteAllByFire_FireId(fireId);
        fireModelRepository.deleteByFireId(fireId);
    }

    public FireModel findFireByFireId(long fireId){
        Optional<FireModel> byId = fireModelRepository.findById(fireId);
        return byId.orElseGet(FireModel::new);
    }

    public int firePerimeter(List<Coordinate> coordinates, Coordinate fireCenter){
        int distance = 0;
        for (Coordinate point : coordinates){
            if (fireCenter.distance(point) > distance){
                distance = (int) fireCenter.distance(point);
            }
        }
        return (int) (2 * Math.PI * distance);
    }

    public ForestFuelType getForestFuelType(int typeId){
        return forestRepository.getForestFuelTypeByTypeId(typeId);
    }
}
