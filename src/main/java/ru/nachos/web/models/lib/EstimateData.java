package ru.nachos.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.nachos.web.models.EstimateDataImpl;

import java.util.List;

@JsonDeserialize(as = EstimateDataImpl.class)
public interface EstimateData {

    int getFireAgentDistance();

    double getWindSpeed();

    double getWindDirection();

    int getFireClass();

    List<String> getCoordinates();

    int getFuelTypeCode();

    int getIterationStepTime();

    int getLastIterationTime();
}
