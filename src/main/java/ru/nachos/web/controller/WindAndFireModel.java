package ru.nachos.web.controller;

import ru.nachos.web.models.Fire;
import ru.nachos.web.models.Wind;

public class WindAndFireModel {

    private Wind wind;

    private Fire fire;

    public WindAndFireModel(){
        fire = new Fire();
        wind = new Wind();
    }

    public WindAndFireModel(Wind wind, Fire fire) {
        this.wind = wind;
        this.fire = fire;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Fire getFire() {
        return fire;
    }

    public void setFire(Fire fire) {
        this.fire = fire;
    }
}
