package git.niklogik.core.controller;

import git.niklogik.core.controller.lib.Controller;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.db.services.FireDatabaseService;

public final class ControllerUtils {
    public static Controller createController(InitialPreprocessingData data, FireDatabaseService fireService){
        return new ControllerImpl(data, fireService);
    }
}
