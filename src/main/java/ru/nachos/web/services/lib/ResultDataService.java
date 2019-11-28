package ru.nachos.web.services.lib;

import org.springframework.stereotype.Service;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.web.models.lib.ResultData;

import java.util.Map;

@Service
public interface ResultDataService {
    ResultData prepareResult(Map<Id<Agent>, Agent> agents, Config config);
}
