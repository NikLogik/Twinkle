package ru.nachos.db.repository.fire;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nachos.db.model.fire.ContourLine;

import java.util.List;

public interface ContourLineRepository extends JpaRepository<ContourLine, Long>{
    @Query(value = "select lines.id, lines.elevation, lines.horizontal from contour_lines as lines where st_contains(?1,lines.horizontal) or st_intersects(?1,lines.horizontal)", nativeQuery = true)
    List<ContourLine> findAllContourLineIsInsideGeometry(Polygon geometry);
    @Query(value = "select lines.id, lines.elevation, lines.horizontal from contour_lines as lines where st_intersects(?1, lines.horizontal)", nativeQuery = true)
    List<ContourLine> findContourLineByLine(Geometry geometry);
}
