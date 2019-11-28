package ru.nachos.web.models;

import lombok.Getter;
import lombok.Setter;
@Deprecated
public class Fire {

    @Getter
    @Setter
    private String fireSpeed;

    @Getter
    @Setter
    private String direction;

    public Fire(){}

    public Fire(String fireSpeed, String direction) {
        this.fireSpeed = fireSpeed;
        this.direction = direction;
    }
}
