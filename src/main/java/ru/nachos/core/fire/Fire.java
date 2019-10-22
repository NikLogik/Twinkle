package ru.nachos.core.fire;

import ru.nachos.core.Coord;
import ru.nachos.core.Id;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.FireFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class Fire {

    private String name;

    private Coord center;
    
    private int perimeter;

    private int accuracy;

    private double fireSpeed;

    private Map<Id<Agent>, Agent> twinkles = new LinkedHashMap<>();

    private FireFactory factory;

    Fire(FireFactory factory){
        this.factory = factory;
    }

    FireFactory getFactory(){ return this.factory; }

    public String getName() {
        return name;
    }

    public Coord getCenter() {
        return center;
    }

    public int getPerimeter() {
        return perimeter;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public double getFireSpeed() {
        return fireSpeed;
    }

    public Map<Id<Agent>, Agent> getTwinkles() {
        return twinkles;
    }

    void setName(String name){
        this.name = name;
    }

    void setCenter(Coord center){
        this.center = center;
    }

    void setPerimeter(int perimeter){
        this.perimeter = perimeter;
    }

    void setAccuracy(int accuracy){
        this.accuracy = accuracy;
    }

    void setFireSpeed(double fireSpeed){
        this.fireSpeed = fireSpeed;
    }
}
