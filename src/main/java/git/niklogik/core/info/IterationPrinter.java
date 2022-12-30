package git.niklogik.core.info;

import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.lib.InitialPreprocessingData;

import java.util.List;

public interface IterationPrinter {
    void info(Config config, InitialPreprocessingData data);

    void printGeometryTypes(List<String> polygons);
}
