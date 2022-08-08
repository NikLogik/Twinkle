package ru.nachos.db.entities.fire;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Entity
@Table(name = "contour_lines", schema = "public")
public class ContourLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "elevation")
    private double elevation;
    @Column(columnDefinition = "geometry(LineString,4326)")
    private LineString horizontal;

    public ContourLine() {
    }

    public ContourLine(double elevation, LineString horizontal) {
        this.elevation = elevation;
         this.horizontal = horizontal;
    }

    public Geometry getHorizontal() {
        return this.horizontal;
    }

    public Double getElevation() {
        return this.elevation;
    }

    public Long getId() {
        return this.id;
    }
}
