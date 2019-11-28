package ru.nachos.core;

import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;

public interface FireModelRunner {
    void run(EstimateData estimateData);
    ResultData getResultData();
}
