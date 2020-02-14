package ru.nachos.db.repository.osm;

import com.vividsolutions.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nachos.db.entities.osm.PolygonOsmModel;

import java.util.List;

public interface PolygonOsmModelRepository extends JpaRepository<PolygonOsmModel, Long> {

    List<PolygonOsmModel> findPolygonOsmModelsByNatural(String natural);

    @Query(value = "select p.osm_id, p.natural, p.landuse, p.water, p.waterway, p.way from planet_osm_polygon p where st_contains(?1,p.way) or st_intersects(?1,p.way)", nativeQuery = true)
    List<PolygonOsmModel> findAllPolygonOsmModelsInsideWay(Polygon way);

    @Query(value = "select Find_SRID('public', 'planet_osm_polygon', 'way') as srid", nativeQuery = true)
    int getSRID();
}
