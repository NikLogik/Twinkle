package git.niklogik.db.entities.fire;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "contour_lines", schema = "public")
public class ContourLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @Column(name = "elevation", nullable = false)
    private double elevation;
    @NotNull
    @Column(name = "horizontal", columnDefinition = "geometry(LineString,4326)")
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
