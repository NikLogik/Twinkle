package ru.nachos.core.info;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.web.models.lib.ResultData;

public interface IterationInfo {
    void info(Config config, InitialPreprocessingData data);

    void printResultData(ResultData resultData);
}
