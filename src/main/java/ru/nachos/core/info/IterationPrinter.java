package ru.nachos.core.info;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;

import java.util.List;

public interface IterationPrinter {
    void info(Config config, InitialPreprocessingData data);

    void printGeometryTipes(List<String> polygons);
}
