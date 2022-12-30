package git.niklogik.core;

import git.niklogik.core.config.ConfigUtils;
import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.ControllerUtils;
import git.niklogik.core.controller.lib.Controller;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.core.fire.FireModel;
import git.niklogik.db.services.ContourLineService;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.db.services.GeometryDatabaseService;
import git.niklogik.web.models.lib.RequestData;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import git.niklogik.core.controller.InitialPreprocessingDataUtils;
import git.niklogik.core.network.NetworkUtils;

import java.util.List;

@RequestScope
@Service
public class FireModelRunner {

    Logger logger = LoggerFactory.getLogger(FireModelRunner.class);

    private final GeometryDatabaseService geometryService;
    private final FireDatabaseService fireService;
    private final ContourLineService lineService;
    private FireModel model;
    private final int osmDatabaseSrid;

    @Autowired
    public FireModelRunner(GeometryDatabaseService geometryService,
                           FireDatabaseService fireService,
                           ContourLineService lineService,
                           @Value("${app.database.osm.srid}")
                           Integer osmDatabaseSrid) {
        this.geometryService = geometryService;
        this.fireService = fireService;
        this.lineService = lineService;
        this.osmDatabaseSrid = osmDatabaseSrid;
    }

    public void run(RequestData requestData) {
        Config config = createConfig(requestData);
        InitialPreprocessingData initialData = InitialPreprocessingDataUtils.createInitialData(config, osmDatabaseSrid);
        InitialPreprocessingDataUtils.loadInitialData(initialData, geometryService, fireService, lineService);
        Controller controller = ControllerUtils.createController(initialData, fireService);
        controller.run();
        this.model = controller.getModel();
    }

    private Config createConfig(RequestData requestData) {
        Coordinate fireCenterCoordinate = new Coordinate(calculateFireCenter(requestData.getFireCenter()));
        int perimeter = fireService.firePerimeter(requestData.getFireCenter(), fireCenterCoordinate);
        int lastIteration = requestData.getLastIterationTime() / requestData.getIterationStepTime();
        return new ConfigUtils.ConfigBuilder(ConfigUtils.createConfig())
                .setStartTime(0)
                .setEndTime(requestData.getLastIterationTime())
                .setSRID(osmDatabaseSrid)
                .setIterationStepTime(requestData.getIterationStepTime())
                .setFireAgentDIstance(requestData.getFireAgentDistance())
                .setFuelType(requestData.getFuelTypeCode())
                .setFireClass(requestData.getFireClass())
                .setWindDirection(requestData.getWindDirection())
                .setWindSpeed(requestData.getWindSpeed())
                .setFireCenterCoordinate(fireCenterCoordinate)
                .setLastIteration(lastIteration)
                .setFirePerimeter(perimeter)
                .build();
    }

    private Coordinate calculateFireCenter(List<Coordinate> coordinateList) {
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

    public long getModelId() {
        return model.getFireId();
    }
}
