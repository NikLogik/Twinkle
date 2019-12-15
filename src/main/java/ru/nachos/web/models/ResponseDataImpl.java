package ru.nachos.web.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.nachos.web.models.lib.ResponseData;

@JsonDeserialize
@JsonSerialize
public class ResponseDataImpl implements ResponseData {
    private int currentIterNum;
    private int iterNum;
    private CoordinateJson[] coordinates;

    public ResponseDataImpl(int iterNum, int currentIterNum, CoordinateJson[] coordinates) {
        this.iterNum = iterNum;
        this.currentIterNum = currentIterNum;
        this.coordinates = coordinates;
    }

    @Override
    public int getCurrentIterNum() {
        return this.currentIterNum;
    }

    @Override
    public int getIterNum() { return this.iterNum; }

    @Override
    public CoordinateJson[] getData() { return coordinates; }
}
