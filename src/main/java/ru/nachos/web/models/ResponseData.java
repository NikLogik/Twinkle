package ru.nachos.web.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize
public class ResponseData {
    private int iterNum;
    private int currentIterNum;
    private List<CoordinateJson> coordinates;

    public ResponseData(int iterNum, int currentIterNum, List<CoordinateJson> coordinates) {
        this.iterNum = iterNum;
        this.currentIterNum = currentIterNum;
        this.coordinates = coordinates;
    }

    public int getIterNum() {
        return this.iterNum;
    }

    public int getCurrentIterNum() {
        return this.currentIterNum;
    }

    public List<CoordinateJson> getCoordinates() {
        return this.coordinates;
    }
}
