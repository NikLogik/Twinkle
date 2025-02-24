package git.niklogik.db.services;

import git.niklogik.config.ApplicationConfig.EPSGProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class CoordinateTransformationService {

    private final EPSGProperties properties;

    public MathTransform getMathTransformation(TransformDirection direction) {
        return switch (direction) {
            case CLIENT_TO_OSM_DB, FIRE_DB_TO_OSM_DB ->
                getMathTransform(properties.getWgs84(), properties.getWebMercator());
            case OSM_DB_TO_CLIENT, OSM_DB_TO_FIRE_DB ->
                getMathTransform(properties.getWebMercator(), properties.getWgs84());
        };
    }

    public Coordinate transform(Coordinate coordinate, MathTransform transform) {
        Coordinate result = null;
        try {
            result = JTS.transform(coordinate, coordinate, transform);
        } catch (TransformException e) {
            log.error(e.getMessage(), e);
        }
        return requireNonNull(result);
    }

    private MathTransform getMathTransform(Integer fromSystemCode, Integer toSystemCode) {
        try {
            var fromCRS = getReferenceSystem(fromSystemCode);
            var toCRS = getReferenceSystem(toSystemCode);
            return CRS.findMathTransform(fromCRS, toCRS);
        } catch (FactoryException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public int getClientSRID() {
        return properties.getWgs84();
    }

    public int getFireDatabaseSRID() {
        return properties.getWgs84();
    }

    public int getOsmDatabaseSRID() {
        return properties.getWebMercator();
    }

    private CoordinateReferenceSystem getReferenceSystem(Integer systemCode) throws FactoryException {
        return CRS.decode(format("EPSG:%s", systemCode), true);
    }

    public enum TransformDirection {
        CLIENT_TO_OSM_DB,
        OSM_DB_TO_CLIENT,
        OSM_DB_TO_FIRE_DB,
        FIRE_DB_TO_OSM_DB
    }
}
