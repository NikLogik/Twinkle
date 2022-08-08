package ru.nachos.db.entities.osm;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "planet_osm_polygon", schema = "public")
public class PolygonOsmModel {

    private Long osm_id;
    private String natural;
    private String landuse;
    private String water;
    private String waterway;
    @Id
    private Geometry way;

    public Long getOsm_id() {
        return osm_id;
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
