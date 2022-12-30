package git.niklogik.db.entities.osm;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public Long getOsmId() {
        return osmId;
    }

    public String getNatural() {
        return natural;
    }

    public String getLanduse() {
        return landuse;
    }

    public String getWater() {
        return water;
    }

    public String getWaterway() {
        return waterway;
    }

    public Geometry getWay() {
        return way;
    }
}
