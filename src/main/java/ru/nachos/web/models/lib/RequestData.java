package ru.nachos.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.web.models.RequestDataImpl;

import java.util.List;

@JsonDeserialize(as = RequestDataImpl.class)
@JsonSerialize(as = RequestDataImpl.class)
public interface RequestData {

    int getFireAgentDistance();

    double getWindSpeed();

    double getWindDirection();

    int getFireClass();

    List<Coordinate> getFireCenter();

    int getFuelTypeCode();

    int getIterationStepTime();

    int getLastIterationTime();
}
