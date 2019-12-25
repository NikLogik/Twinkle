package ru.nachos.db.model.fire;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fire_info", schema = "public")
@Data
public class FireModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fire_id")
    private Long fireId;
    private double direction;
    @Column(name = "fire_speed")
    private double fireSpeed;
    @Column(name = "fire_class")
    private int fireClass;
    private int iterations_num;
    @Column(columnDefinition = "geometry(Geometry,4326)")
    private Geometry center;
    @OneToMany(mappedBy = "fire")
    private Set<FireFrontModel> models = new HashSet<>(0);

    public FireModel(){}

    public FireModel(double direction, double fireSpeed, int fireClass, int iterations_num, Geometry center){
        this.direction = direction;
        this.fireSpeed = fireSpeed;
        this.fireClass = fireClass;
        this.iterations_num = iterations_num;
        this.center = center;
    }
}
