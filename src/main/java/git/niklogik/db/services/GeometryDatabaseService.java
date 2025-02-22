package git.niklogik.db.services;

import git.niklogik.config.ApplicationConfig.EPSGProperties;
import git.niklogik.core.Id;
import git.niklogik.core.network.lib.Network;
import git.niklogik.core.network.lib.NetworkFactory;
import git.niklogik.core.network.lib.PolygonV2;
import git.niklogik.core.utils.PolygonType;
import git.niklogik.db.entities.osm.PolygonOsmModel;
import git.niklogik.db.repository.osm.PolygonOsmModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor()
public class GeometryDatabaseService {

    private final PolygonOsmModelRepository polygonRepository;
    private final EPSGProperties properties;

    public void createNetworkFromBoundaryBox(Network network, Coordinate[] boundaryBox) {
        Polygon polygon = (Polygon) new GeometryFactory().createPolygon(
                                                             new Coordinate[]{boundaryBox[0], boundaryBox[1], boundaryBox[2], boundaryBox[3], boundaryBox[0]})
                                                         .getEnvelope();
        polygon.setSRID(properties.getWebMercator());
        List<PolygonOsmModel> polygons = polygonRepository.findAllInsideGeom(polygon);
        log.info("<=========================== Loaded polygons {} ===========================>", polygons.size());
        network.getPolygones().putAll(sortByType(network.getFactory(), polygons));
    }

    private Map<PolygonType, Map<Id<PolygonV2>, PolygonV2>> sortByType(NetworkFactory factory, List<PolygonOsmModel> polygons) {
        Set<Long> ids = new TreeSet<>();
        long counter = 1000;
        List<PolygonV2> polygonV2s = new ArrayList<>();
        for (PolygonOsmModel polygon : polygons) {
            long id = polygon.getOsmId();
            if (polygon.getWay() instanceof MultiPolygon) {
                for (int i = 0; i < polygon.getWay().getNumGeometries(); i++) {
                    polygonV2s.add(
                        factory.createPolygon(id + ":multi-" + counter, (Polygon) polygon.getWay().getGeometryN(i),
                                              polygon));
                    counter += 100;
                }
            } else {
                if (ids.contains(id)) {
                    id += counter;
                    polygonV2s.add(factory.createPolygon(id + ":duplicate", polygon));
                    counter += 100;
                } else {
                    polygonV2s.add(factory.createPolygon(id, polygon));
                    ids.add(id);
                }
            }
        }
        return polygonV2s.stream()
                         .collect(
                             Collectors.groupingBy(
                                 PolygonV2::getPolygonType,
                                 Collectors.toMap(PolygonV2::getId, Function.identity()))
                         );
    }
}
