package git.niklogik.core;

import git.niklogik.core.config.ConfigUtils;
import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.ControllerUtils;
import git.niklogik.core.controller.InitialPreprocessingDataImpl;
import git.niklogik.core.controller.InitialPreprocessingDataUtils;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.db.services.ContourLineService;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.db.services.GeometryDatabaseService;
import git.niklogik.web.models.CoordinateJson;
import git.niklogik.web.models.CreateFireRequest;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@Service
public class FireModelRunner {

    private final Logger logger = LoggerFactory.getLogger(FireModelRunner.class);

    private final GeometryDatabaseService geometryService;
    private final FireDatabaseService fireService;
    private final ContourLineService lineService;
    private final int osmDatabaseSrid;

    @Autowired
    public FireModelRunner(GeometryDatabaseService geometryService,
                           FireDatabaseService fireService,
                           ContourLineService lineService,
                           @Value("${app.database.osm.srid:3857}")
                           Integer osmDatabaseSrid) {
        this.geometryService = geometryService;
        this.fireService = fireService;
        this.lineService = lineService;
        this.osmDatabaseSrid = osmDatabaseSrid;
    }

    public void run(CreateFireRequest requestData) {
        Config config = createConfig(requestData);
        InitialPreprocessingData initialData = new InitialPreprocessingDataImpl(config, osmDatabaseSrid);
        InitialPreprocessingDataUtils.loadInitialData(initialData, geometryService, fireService, lineService);
        ControllerUtils.createController(initialData, fireService).run();
    }

    private Config createConfig(CreateFireRequest requestData) {
        Coordinate fireCenterCoordinate = new Coordinate(calculateFireCenter(requestData.fireCenter()));
        int perimeter = fireService.firePerimeter(requestData.fireCenter(), fireCenterCoordinate);
        int lastIteration = requestData.lastIterationTime() / requestData.iterationStepTime();
        return new ConfigUtils.ConfigBuilder(ConfigUtils.createConfig())
                .setStartTime(0)
                .setEndTime(requestData.lastIterationTime())
                .setSRID(osmDatabaseSrid)
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

    private Coordinate calculateFireCenter(List<CoordinateJson> coordinateList) {
        if (coordinateList.size() == 1) {
            return coordinateList.get(0);
        } else if (coordinateList.size() == 2) {
            return NetworkUtils.centerLine(coordinateList.get(0), coordinateList.get(1));
        } else {
            return new GeometryFactory()
                    .createMultiPointFromCoords(coordinateList.toArray(Coordinate[]::new))
                    .getCentroid().getCoordinate();
        }
    }
}
