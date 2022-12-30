package git.niklogik.db.repository.fire;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import git.niklogik.db.entities.fire.ContourLine;

import java.util.List;

public interface ContourLineRepository extends JpaRepository<ContourLine, Long>{
    @Query(value = "select cl from ContourLine cl where st_contains(:geom, cl.horizontal) or st_intersects(:geom, cl.horizontal)")
    List<ContourLine> findAllInsideGeometry(@Param("geom") Polygon geometry);
    @Query(value = "select cl from ContourLine cl where st_intersects(:geom, cl.horizontal)")
    List<ContourLine> findIntersected(@Param("geom") Geometry geometry);
}