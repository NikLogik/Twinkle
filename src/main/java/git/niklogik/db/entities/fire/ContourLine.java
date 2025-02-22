package git.niklogik.db.entities.fire;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.locationtech.jts.geom.LineString;

@Getter

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
}
