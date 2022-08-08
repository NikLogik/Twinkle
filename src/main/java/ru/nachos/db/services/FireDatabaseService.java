package ru.nachos.db.services;

import org.geotools.geometry.jts.JTS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.fire.FireModel;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.db.entities.fire.FireDAO;
import ru.nachos.db.entities.fire.FireInfoDAO;
import ru.nachos.db.entities.fire.FireIterationDAO;
import ru.nachos.db.entities.fire.ForestFuelType;
import ru.nachos.db.repository.fire.FireIterationRepository;
import ru.nachos.db.repository.fire.FireRepository;
import ru.nachos.db.repository.fire.ForestFuelTypeRepository;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseDataImpl;
import ru.nachos.web.models.lib.ResponseData;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FireDatabaseService {

    private ForestFuelTypeRepository forestRepository;
    private CoordinateTransformationService transformationService;
    private FireRepository fireRepository;
    private FireIterationRepository iterationRepository;

    @Autowired
    public FireDatabaseService(CoordinateTransformationService transformationService, ForestFuelTypeRepository forestRepository,
                               FireRepository fireRepository, FireIterationRepository iterationRepository){

        this.transformationService = transformationService;
        this.forestRepository = forestRepository;
        this.fireRepository = fireRepository;
        this.iterationRepository = iterationRepository;
    }

    public FireModel createAndGetFireModel(Fire fire, Point center, int iterAmount, int forestType){
        FireInfoDAO fireInfoDAO = new FireInfoDAO(fire.getHeadDirection(), (int)fire.getFireSpeed(), fire.getFireClass(), center);
        ForestFuelType forestId = forestRepository.getForestFuelTypeByTypeId(forestType);
        FireDAO fireDAO = fireRepository.save(new FireDAO(fire.getName(), new Date(), forestId, fireInfoDAO));
        FireModel model = new FireModel(fireDAO, iterAmount);
        return model;
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
        FireDAO one = fireRepository.findById(model.getFireId()).get();
        FireIterationDAO fireIterationDAO = new FireIterationDAO(currentIteration, model.getIterAmount(), front, one);
        return iterationRepository.save(fireIterationDAO)!= null;
    }

    public ResponseData getResponseDataByFireIdAndIterNumber(long fireId, int iterNumber){
        FireIterationDAO fireFrontModel = iterationRepository.findFireIterationDAOByFireId_IdAndIterNumber(fireId, iterNumber);
        Geometry polygon = fireFrontModel.getPolygon();
        CoordinateJson[] collect = Arrays.stream(((Polygon) polygon).getExteriorRing().getCoordinates()).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).toArray(CoordinateJson[]::new);
        return new ResponseDataImpl(fireId, fireFrontModel.getIterAmount(), fireFrontModel.getIterNumber(), collect);
    }

    public ResponseData getResponseDataByFireIdWhereIterNumberIsMin(long fireId){
        FireIterationDAO firstIter = iterationRepository.findFirstIterByFireId(fireId);
        Geometry polygon = firstIter.getPolygon();
        CoordinateJson[] collect = Arrays.stream(((Polygon) polygon).getExteriorRing().getCoordinates()).map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y)).toArray(CoordinateJson[]::new);
        return new ResponseDataImpl(fireId, firstIter.getIterAmount(), firstIter.getIterNumber(), collect);
    }

    public void deleteFireById(long fireId){
        iterationRepository.deleteAllByFireId_Id(fireId);
        fireRepository.deleteById(fireId);
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
