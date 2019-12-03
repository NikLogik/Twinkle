package ru.nachos.web.services.lib;

import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.web.models.lib.ResultData;

import java.util.Map;

public interface ResultDataService {
    ResultData prepareResult(Map<Id<Agent>, Agent> agents, InitialPreprocessingData data);
}
