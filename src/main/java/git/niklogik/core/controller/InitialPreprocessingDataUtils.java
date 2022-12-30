package git.niklogik.core.controller;

import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.db.services.ContourLineService;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.db.services.GeometryDatabaseService;

public class InitialPreprocessingDataUtils {

    public static InitialPreprocessingData createInitialData(Config config, int osmDatabaseSrid){
        if (config == null){
            throw new NullPointerException("Config must not be null");
        } else {
            return new InitialPreprocessingDataImpl(config, osmDatabaseSrid);
        }
    }

    public static InitialPreprocessingData loadInitialData(InitialPreprocessingData data, GeometryDatabaseService geometryService, FireDatabaseService fireService,
                                                           ContourLineService lineService){
        InitialPreprocessingDataLoader loader = new InitialPreprocessingDataLoader(data, geometryService, fireService, lineService);
        return loader.loadPreprocessingData();
    }
}
