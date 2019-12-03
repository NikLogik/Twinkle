package ru.nachos.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.web.models.EstimateDataImpl;

import java.util.List;

@JsonDeserialize(as = EstimateDataImpl.class)
public interface EstimateData {

    int getFireAgentDistance();

    double getWindSpeed();

    double getWindDirection();

    int getFireClass();

    List<Coordinate> getFireCenter();

    int getFuelTypeCode();

    int getIterationStepTime();

    int getLastIterationTime();
}
