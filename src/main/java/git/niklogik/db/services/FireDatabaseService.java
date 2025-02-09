package git.niklogik.db.services;

import git.niklogik.core.fire.FireModel;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.db.entities.fire.FireDAO;
import git.niklogik.db.entities.fire.FireInfoDAO;
import git.niklogik.db.entities.fire.FireIterationDAO;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import git.niklogik.db.repository.fire.FireIterationRepository;
import git.niklogik.db.repository.fire.FireRepository;
import git.niklogik.db.repository.fire.ForestFuelTypeRepository;
import git.niklogik.web.NotFoundException;
import git.niklogik.web.models.CoordinateJson;
import git.niklogik.web.models.FireDataResponse;
import org.geotools.geometry.jts.JTS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@Service
public class FireDatabaseService {

    private final Logger logger = LoggerFactory.getLogger(FireDatabaseService.class);

    private final ForestFuelTypeRepository forestRepository;
    private final CoordinateTransformationService transformationService;
    private final FireRepository fireRepository;
    private final FireIterationRepository iterationRepository;

    public FireDatabaseService(CoordinateTransformationService transformationService, ForestFuelTypeRepository forestRepository, FireRepository fireRepository, FireIterationRepository iterationRepository) {

        this.transformationService = transformationService;
        this.forestRepository = forestRepository;
        this.fireRepository = fireRepository;
        this.iterationRepository = iterationRepository;
    }

    public FireModel createAndGetModel(Fire fire, Point center, int iterAmount, int forestType) {
        FireInfoDAO fireInfoDAO = new FireInfoDAO(fire.getHeadDirection(), (int) fire.getFireSpeed(),
                                                  fire.getFireClass(), center);
        ForestFuelTypeDao forestId = forestRepository.findByTypeId(forestType)
                                                     .orElseThrow(() -> new NotFoundException("Fuel type not found"));
        FireDAO fireDAO = fireRepository.save(new FireDAO(fire.getName(), new Date(), forestId, fireInfoDAO));
        return new FireModel(fireDAO, iterAmount);
    }

    public void saveIteration(int currentIteration, Polygon front, FireModel model) {
        MathTransform mathTransformation = transformationService.getMathTransformation(
            CoordinateTransformationService.TransformDirection.osmDB_to_fireDB);
        Coordinate[] coordinates = front.getCoordinates();
        try {
            for (Coordinate coordinate : coordinates) {
                coordinate.setCoordinate(JTS.transform(coordinate, null, mathTransformation));
            }
        } catch (TransformException ex) {
            logger.error(ex.getMessage(), ex);
        }
        front.setSRID(transformationService.getFireDatabaseSRID());
        FireDAO one = fireRepository.findById(model.getFireId())
                                    .orElseThrow(() -> new NotFoundException(
                                        format("Fire not found with id: %s", model.getFireId())
                                    ));
        FireIterationDAO fireIterationDAO = new FireIterationDAO(currentIteration, model.getIterAmount(), front, one);
        iterationRepository.save(fireIterationDAO);
    }

    public FireDataResponse findIterationData(long fireId, int iterNumber) {
        FireIterationDAO fireFrontModel = iterationRepository.findFireIterationDAOByFireId_IdAndIterNumber(fireId,
                                                                                                           iterNumber);
        Geometry polygon = fireFrontModel.getPolygon();
        var collect = Arrays.stream(((Polygon) polygon).getExteriorRing().getCoordinates())
                            .map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y))
                            .toList();
        return new FireDataResponse(fireId, fireFrontModel.getIterAmount(), fireFrontModel.getIterNumber(), collect);
    }

    public FireDataResponse findFirstIterationData(long fireId) {
        FireIterationDAO firstIter = iterationRepository.findFirstIterByFireId(fireId);
        Polygon polygon = (Polygon) firstIter.getPolygon();
        var collect = Arrays.stream(polygon.getExteriorRing().getCoordinates())
                            .map(coordinate -> new CoordinateJson(coordinate.x, coordinate.y))
                            .toList();
        return new FireDataResponse(fireId, firstIter.getIterAmount(), firstIter.getIterNumber(), collect);
    }

    public void deleteById(Long fireId) {
        iterationRepository.deleteAllByFireId_Id(fireId);
        fireRepository.deleteById(fireId);
    }

    public int firePerimeter(List<CoordinateJson> coordinates, Coordinate fireCenter) {
        int distance = 0;
        for (Coordinate point : coordinates) {
            if (fireCenter.distance(point) > distance) {
                distance = (int) fireCenter.distance(point);
            }
        }
        return (int) (2 * Math.PI * distance);
    }

    public ForestFuelTypeDao getForestFuelType(Integer typeId) {
        return forestRepository.findByTypeId(typeId).orElseThrow(() -> new NotFoundException("Fuel type not found"));
    }
}
