package git.niklogik.web.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import git.niklogik.web.models.lib.ResponseData;

@JsonDeserialize
@JsonSerialize
public class ResponseDataImpl implements ResponseData {
    private long fireId;
    private int currentIterNum;
    private int iterNum;
    private CoordinateJson[] coordinates;

    public ResponseDataImpl(long fireId, int iterNum, int currentIterNum, CoordinateJson[] coordinates) {
        this.fireId = fireId;
        this.iterNum = iterNum;
        this.currentIterNum = currentIterNum;
        this.coordinates = coordinates;
    }

    @Override
    public long getFireId() { return this.fireId; }

    @Override
    public int getCurrentIterNum() {
        return this.currentIterNum;
    }

    @Override
    public int getIterNum() { return this.iterNum; }

    @Override
    public CoordinateJson[] getData() { return coordinates; }
}
