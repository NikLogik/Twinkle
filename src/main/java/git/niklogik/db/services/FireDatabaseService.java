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
import git.niklogik.error.exception.FireModelNotFoundException;
import git.niklogik.error.exception.FuelTypeNotFoundException;
import git.niklogik.web.models.FireDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static git.niklogik.db.services.CoordinateTransformationService.TransformDirection.OSM_DB_TO_FIRE_DB;

@Slf4j
@Service
@RequiredArgsConstructor
public class FireDatabaseService {

    private final ForestFuelTypeRepository forestRepository;
    private final CoordinateTransformationService transformationService;
    private final FireRepository fireRepository;
    private final FireIterationRepository iterationRepository;

    public FireModel createAndGetModel(Fire fire, Point center, int iterAmount, int fuelTypeId) {
        FireInfoDAO fireInfoDAO = new FireInfoDAO(fire.getHeadDirection(), fire.getFireSpeed().doubleValue(),
                                                  fire.getFireClass(), center);
        var fuelType = getForestFuelType(fuelTypeId);
        FireDAO fireDAO = fireRepository.save(new FireDAO(fire.getName(), new Date(), fuelType, fireInfoDAO));
        return new FireModel(fireDAO, iterAmount);
    }

    public void saveIteration(int currentIteration, Polygon front, FireModel model) {
        var mathTransformation = transformationService.getMathTransformation(OSM_DB_TO_FIRE_DB);
        for (Coordinate coordinate : front.getCoordinates()) {
            transformationService.transform(coordinate, mathTransformation);
        }
        front.setSRID(transformationService.getFireDatabaseSRID());
        FireDAO one = fireRepository.findById(model.getFireId())
                                    .orElseThrow(() -> new FireModelNotFoundException(model.getFireId()));
        FireIterationDAO fireIterationDAO = new FireIterationDAO(currentIteration, model.getIterAmount(), front, one);
        iterationRepository.save(fireIterationDAO);
    }

    public FireDataResponse findIterationData(long fireId, int iterNumber) {
        FireIterationDAO fireFrontModel = iterationRepository.findFireIterationDAOByFireId_IdAndIterNumber(fireId,
                                                                                                           iterNumber);
        Geometry polygon = fireFrontModel.getPolygon();
        var collect = Arrays.stream(((Polygon) polygon).getExteriorRing().getCoordinates())
                            .map(coordinate -> new Coordinate(coordinate.x, coordinate.y))
                            .toList();
        return new FireDataResponse(fireId, fireFrontModel.getIterAmount(), fireFrontModel.getIterNumber(), collect);
    }

    public FireDataResponse findFirstIterationData(long fireId) {
        FireIterationDAO firstIter = iterationRepository.findFirstIterByFireId(fireId);
        Polygon polygon = (Polygon) firstIter.getPolygon();
        var collect = Arrays.stream(polygon.getExteriorRing().getCoordinates())
                            .map(coordinate -> new Coordinate(coordinate.x, coordinate.y))
                            .toList();
        return new FireDataResponse(fireId, firstIter.getIterAmount(), firstIter.getIterNumber(), collect);
    }

    public void deleteById(Long fireId) {
        iterationRepository.deleteAllByFireId_Id(fireId);
        fireRepository.deleteById(fireId);
    }

    public int firePerimeter(List<Coordinate> coordinates, Coordinate fireCenter) {
        int distance = 0;
        for (Coordinate point : coordinates) {
            if (fireCenter.distance(point) > distance) {
                distance = (int) fireCenter.distance(point);
            }
        }
        return (int) (2 * Math.PI * distance);
    }

    public ForestFuelTypeDao getForestFuelType(Integer typeId) {
        return forestRepository.findByTypeId(typeId)
                               .orElseThrow(() -> new FuelTypeNotFoundException(typeId));
    }
}
