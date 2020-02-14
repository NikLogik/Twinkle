package ru.nachos.db.services;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.network.lib.Network;
import ru.nachos.db.entities.fire.ContourLine;
import ru.nachos.db.repository.fire.ContourLineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
public class ContourLineService {

    private CoordinateTransformationService transformationService;
    private ContourLineRepository lineRepository;

    @Autowired
    public ContourLineService(ContourLineRepository lineRepository, CoordinateTransformationService transformationService){
        this.lineRepository = lineRepository;
        this.transformationService = transformationService;
    }

    public Network getContourLines(Network network, Coordinate[] boundaryBox){
        TreeMap<Long, ContourLine> relief = new TreeMap<>();
        List<ContourLine> allContourLineIsInsideGeometry = null;
        Coordinate[] coordinates = boundaryBox;
        Polygon polygon = network.getFactory().getGeomFactory().createPolygon(new Coordinate[]{coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[0]});
        polygon.setSRID(transformationService.getOsmDatabaseSRID());
        allContourLineIsInsideGeometry = lineRepository.findAllContourLineIsInsideGeometry(polygon);
        allContourLineIsInsideGeometry.forEach(contourLine -> relief.put(contourLine.getId(), contourLine));
        network.addAllReliefLines(relief);
        return network;
    }

    public List<Coordinate> getIntersectionPoints(Geometry geom1){
        List<ContourLine> candidates = lineRepository.findContourLineByLine(geom1);
        List<Coordinate> intersections = new ArrayList<>();
        for (ContourLine line : candidates){
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
