package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;
import ru.nachos.db.entities.fire.FireDAO;

@Data
public class FireModel {

    private Long fireId;
    private double direction;
    private double fireSpeed;
    private int fireClass;
    private int iterAmount;
    private Geometry center;
    public FireModel(){}

    public FireModel(FireDAO fireDAO, int iterAmount){
        this.fireId = fireDAO.getId();
        this.direction = fireDAO.getFireInfo().getDirection();
        this.fireSpeed = fireDAO.getFireInfo().getFireSpeed();
        this.fireClass = fireDAO.getFireInfo().getFireClass();
        this.iterAmount = iterAmount;
        this.center = fireDAO.getFireInfo().getCenter();
    }

    public FireModel(double direction, double fireSpeed, int fireClass, int iterations_num, Geometry center){
        this.direction = direction;
        this.fireSpeed = fireSpeed;
        this.fireClass = fireClass;
        this.iterAmount = iterations_num;
        this.center = center;
    }
}
