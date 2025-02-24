package git.niklogik.db.services;

import git.niklogik.core.network.lib.Network;
import git.niklogik.db.entities.fire.ContourLine;
import git.niklogik.db.repository.fire.ContourLineRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ContourLineService {

    private final CoordinateTransformationService transformationService;
    private final ContourLineRepository lineRepository;

    public void getContourLines(Network network, Coordinate[] boundaryBox) {
        TreeMap<Long, ContourLine> relief = new TreeMap<>();
        List<ContourLine> allContourLineIsInsideGeometry;
        Polygon polygon = new GeometryFactory().createPolygon(
            new Coordinate[]{boundaryBox[0], boundaryBox[1], boundaryBox[2], boundaryBox[3], boundaryBox[0]});
        polygon.setSRID(transformationService.getOsmDatabaseSRID());
        allContourLineIsInsideGeometry = lineRepository.findAllInsideGeometry(polygon);
        allContourLineIsInsideGeometry.forEach(contourLine -> relief.put(contourLine.getId(), contourLine));
        network.addAllReliefLines(relief);
    }

    public List<Coordinate> getIntersectionPoints(Geometry geom1) {
        List<ContourLine> candidates = lineRepository.findIntersected(geom1);
        List<Coordinate> intersections = new ArrayList<>();
        for (ContourLine line : candidates) {
            Geometry intersection = geom1.intersection(line.getHorizontal());
            for (int j = 0; j < intersection.getCoordinates().length; j++) {
                Coordinate coordinate = intersection.getCoordinates()[j];
                coordinate.setOrdinate(2, line.getElevation());
                intersections.add(coordinate);
            }
        }
        return intersections;
    }

}
