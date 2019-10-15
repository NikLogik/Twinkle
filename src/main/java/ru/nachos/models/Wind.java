package ru.nachos.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Map;

public class Wind {

    private String windSpeed;

    private String direction;

    Map<String, Object> details;

    /**
     * Empty constructor for initializing
     */
    public Wind() {}

    public Wind(String speed, String direction) {
        this.windSpeed = speed;
        this.direction = direction;
    }

    @JsonAnySetter
    void setDetails(String key, Object value){
        details.put(key, value);
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
