package git.niklogik.db.entities.osm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.locationtech.jts.geom.Geometry;

@Getter
@Entity
@Table(name = "planet_osm_polygon")
public class PolygonOsmModel {

    @Column(name = "osm_id")
    private Long osmId;
    private String natural;
    private String landuse;
    private String water;
    private String waterway;
    @Id
    private Geometry way;
}
