package ru.nachos.web.services.lib;

import org.springframework.stereotype.Service;
import ru.nachos.web.models.lib.EstimateData;
import ru.nachos.web.models.lib.ResultData;

@Service
public interface EstimateDataService {
    boolean validationData(EstimateData estimateData);

    void start(EstimateData estimateData);

    ResultData getResult();
}
