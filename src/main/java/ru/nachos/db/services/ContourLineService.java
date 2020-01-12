package ru.nachos.db.services;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;
import org.geotools.geometry.jts.JTS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nachos.core.network.lib.Network;
import ru.nachos.db.model.fire.ContourLine;
import ru.nachos.db.repository.fire.ContourLineRepository;

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
        MathTransform mathTransformation = transformationService.getMathTransformation(CoordinateTransformationService.TransformDirection.osmDB_to_fireDB);
        try {
            Coordinate[] coordinates = boundaryBox;
            for (int i=0; i<coordinates.length; i++){
                coordinates[i].setCoordinate(JTS.transform(coordinates[i], null, mathTransformation));
            }
            Polygon polygon = network.getFactory().getGeomFactory().createPolygon(new Coordinate[]{coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[0]});
            polygon.setSRID(transformationService.getFireDatabaseSRID());
            allContourLineIsInsideGeometry = lineRepository.findAllContourLineIsInsideGeometry(polygon);
        } catch (TransformException e) {
            e.printStackTrace();
        }
        MathTransform math = transformationService.getMathTransformation(CoordinateTransformationService.TransformDirection.fireDB_to_osmDB);
        try {
            for (ContourLine line : allContourLineIsInsideGeometry){
                Geometry transform = JTS.transform(line.getHorizontal(), math);
                line.setHorizontal((LineString) transform);
            }
        } catch (TransformException e) {
            e.printStackTrace();
        }
        allContourLineIsInsideGeometry.forEach(contourLine -> relief.put(contourLine.getId(), contourLine));
        network.addAllReliefLines(relief);
        return network;
    }

    private void transformContourLine(ContourLine line, MathTransform transform){
        Coordinate[] coordinates = line.getHorizontal().getCoordinates();
        try {
            for (int i=0; i<coordinates.length; i++){
                line.getHorizontal().getCoordinates()[i].setCoordinate(JTS.transform(coordinates[i], null, transform));
            }
        } catch (TransformException e) {
            e.printStackTrace();
        }
    }
}
