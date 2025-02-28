package git.niklogik.core;

import git.niklogik.config.ApplicationConfig.EPSGProperties;
import git.niklogik.core.config.ConfigUtils;
import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.ControllerUtils;
import git.niklogik.core.controller.InitialPreprocessingDataImpl;
import git.niklogik.core.controller.InitialPreprocessingDataLoader;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.db.services.ContourLineService;
import git.niklogik.db.services.CoordinateTransformationService;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.db.services.GeometryDatabaseService;
import git.niklogik.web.models.CreateFireRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

import static git.niklogik.db.services.CoordinateTransformationService.TransformDirection.CLIENT_TO_OSM_DB;

@RequestScope
@Service
@RequiredArgsConstructor
public class FireModelRunner {

    private final GeometryDatabaseService geometryService;
    private final FireDatabaseService fireService;
    private final ContourLineService lineService;
    private final CoordinateTransformationService transformationService;
    private final EPSGProperties properties;
    private final InitialPreprocessingDataLoader initialPreprocessingDataLoader;

    public void run(CreateFireRequest requestData) {
        Config config = createConfig(requestData);
        InitialPreprocessingData initialData = new InitialPreprocessingDataImpl(config, properties.getWebMercator());
        initialPreprocessingDataLoader.loadPreprocessingData(config, initialData);
        ControllerUtils.createController(initialData, fireService).run();
    }

    private Config createConfig(CreateFireRequest requestData) {
        Coordinate fireCenterCoordinate = new Coordinate(calculateFireCenter(requestData.fireCenter()));
        int perimeter = fireService.firePerimeter(requestData.fireCenter(), fireCenterCoordinate);
        int lastIteration = requestData.lastIterationTime() / requestData.iterationStepTime();
        return new ConfigUtils.ConfigBuilder(ConfigUtils.createConfig())
            .setStartTime(0)
            .setEndTime(requestData.lastIterationTime())
            .setSRID(properties.getWebMercator())
            .setIterationStepTime(requestData.iterationStepTime())
            .setFireAgentDIstance(requestData.fireAgentDistance())
            .setFuelType(requestData.fuelTypeCode())
            .setFireClass(requestData.fireClass())
            .setWindDirection(requestData.windDirection())
            .setWindSpeed(requestData.windSpeed())
            .setFireCenterCoordinate(fireCenterCoordinate)
            .setLastIteration(lastIteration)
            .setFirePerimeter(perimeter)
            .build();
    }

    private Coordinate calculateFireCenter(List<Coordinate> coordinateList) {
        var coordinates = transformCoordinates(coordinateList);
        if (coordinates.size() == 1) {
            return coordinates.getFirst();
        } else if (coordinates.size() == 2) {
            return NetworkUtils.centerLine(coordinates.getFirst(), coordinates.getLast());
        } else {
            return new GeometryFactory()
                .createMultiPointFromCoords(coordinates.toArray(Coordinate[]::new))
                .getCentroid().getCoordinate();
        }
    }

    private List<Coordinate> transformCoordinates(List<Coordinate> coordinateList) {
        var mathTransformation = transformationService.getMathTransformation(CLIENT_TO_OSM_DB);
        return coordinateList.stream()
                             .map(coordinate -> transformationService.transform(coordinate, mathTransformation))
                             .toList();
    }
}
