package ru.nachos.db.entity;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Table(name = "planet_osm_point")
public class PlanetOsmPoint {

    @Id
    @Column(name = "osm_id")
    private long osm_id;

    @Column(name = "way")
    private Geometry way;

    public PlanetOsmPoint() {
    }

    public long getOsmId() {
        return osm_id;
    }

    public Geometry getWay() {
        return way;
    }

    public void setWay(Geometry way) {
        this.way = way;
    }
}
