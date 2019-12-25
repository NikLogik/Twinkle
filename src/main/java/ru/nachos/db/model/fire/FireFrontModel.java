package ru.nachos.db.model.fire;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "fire_iterations", schema = "public")
@Data
public class FireFrontModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "iter_number")
    private int iterNumber;
    @Column(name = "iter_amount")
    private int iterAmount;

    @Column(columnDefinition = "geometry(Geometry,4326)")
    private Geometry polygon;

    @ManyToOne
    @JoinColumn(name = "fire_id")
    private FireModel fire;

    public FireFrontModel() {}

    public FireFrontModel(int iter_amount, int iter_number, Geometry polygon, FireModel fire){
        this.iterAmount = iter_amount;
        this.iterNumber = iter_number;
        this.polygon = polygon;
        this.fire = fire;
    }
}
