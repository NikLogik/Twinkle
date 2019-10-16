package ru.nachos.models;

import lombok.Getter;
import lombok.Setter;

public class Wind {

    @Getter
    @Setter
    private String formId;

    @Getter
    @Setter
    private String windSpeed;

    @Getter
    @Setter
    private String direction;

    public Wind() {}

    public Wind(String windSpeed, String direction) {
        this.windSpeed = windSpeed;
        this.direction = direction;
    }
}
