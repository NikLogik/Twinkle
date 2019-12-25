package ru.nachos.db.model.osm;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "planet_osm_polygon", schema = "public")
@Data
public class PolygonOsmModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long osm_id;
    private String natural;
    private String landuse;
    private String water;
    private String waterway;
    private Geometry way;
}
