package ru.nachos.db.model.fire;

import com.vividsolutions.jts.geom.LineString;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "contour_lines", schema = "public")
@Data
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
}
