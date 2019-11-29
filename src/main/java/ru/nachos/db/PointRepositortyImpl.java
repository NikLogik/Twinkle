package ru.nachos.db;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import org.springframework.stereotype.Repository;
import ru.nachos.db.repository.PointRepository;

import java.util.Map;

@Repository
public class PointRepositortyImpl implements PointRepository {

    @Override
    public Point getPointById(long id) {
        return null;
    }

    @Override
    public Point getNearestPoint(Coordinate coordinate) {
        return null;
    }

    @Override
    public int getSRID() {
        return 0;
    }

    @Override
    public Map<String, Point> getPointsFromBoundaryBox(Coordinate[] coordinates) {
        return null;
    }
}
