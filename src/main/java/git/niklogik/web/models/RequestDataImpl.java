package git.niklogik.web.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import git.niklogik.web.models.lib.RequestData;
import org.locationtech.jts.geom.Coordinate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@JsonDeserialize
@JsonSerialize
public class RequestDataImpl implements RequestData, Serializable {

    private double windSpeed;
    private double windDirection;
    private int fireClass;
    private CoordinateJson[] fireCenter;
    private int fuelTypeCode;
    private int iterationStepTime;
    private int lastIterationTime;
    private int fireAgentDistance;

    @Override
    public Integer getFireAgentDistance() { return fireAgentDistance; }

    @Override
    public Double getWindSpeed() { return this.windSpeed; }

    @Override
    public Double getWindDirection() {
        return this.windDirection;
    }

    @Override
    public int getFireClass() {
        return this.fireClass;
    }

    @Override
    public List<Coordinate> getFireCenter() { return Arrays.asList(this.fireCenter); }

    @Override
    public Integer getFuelTypeCode() { return this.fuelTypeCode; }

    @Override
    public Integer getIterationStepTime() {
        return this.iterationStepTime;
    }

    @Override
    public Integer getLastIterationTime() {
        return this.lastIterationTime;
    }

    @Override
    public String toString() {
        return "RequestDataImpl{" +
                "windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", fireClass=" + fireClass +
                ", fireCenter=" + Arrays.toString(fireCenter) +
                ", fuelTypeCode=" + fuelTypeCode +
                ", iterationStepTime=" + iterationStepTime +
                ", lastIterationTime=" + lastIterationTime +
                ", fireAgentDistance=" + fireAgentDistance +
                '}';
    }
}
