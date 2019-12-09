package ru.nachos.db;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nachos.core.Id;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.repository.PolygonRepository;

import javax.persistence.NonUniqueResultException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PolygonRepositoryImpl implements PolygonRepository {

    @Autowired
    OsmDatabaseManager manager;
    GeometryFactory factory = new GeometryFactory();

    private static WKBReader reader = new WKBReader(new GeometryFactory());
    private static int SRID;

    private static final String GET_SRID = "select Find_SRID('public', 'planet_osm_polygon', 'way') as srid";
    @Override
    public int getSRID() {
        try {
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_SRID);
            resultSet.next();
            int resultSetInt = resultSet.getInt("srid");
            if(resultSetInt != SRID || SRID == 0){
                SRID = resultSetInt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SRID;
    }

    private static final String GET_POLYGON_BY_ID = "select ST_AsEWKB(way) as way from planet_osm_polygon where osm_id=?";
    @Override
    public Polygon getPolygonById(long id) {
        Polygon polygon = null;
        try{
            Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_POLYGON_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.getFetchSize() > 1){
                throw new NonUniqueResultException("Too many results: " + resultSet.getFetchSize());
            }
            resultSet.next();
            polygon = (Polygon) reader.read(resultSet.getBytes("way"));
            statement.close();
            connection.close();
        } catch (SQLException | ParseException e){
            e.printStackTrace();
        }
        return polygon;
    }

    @Override
    public Polygon getPolygonByCoordinate(Coordinate coordinate) {
        Point point = this.factory.createPoint(coordinate);
        WKTWriter writer = new WKTWriter();
        String pointS = writer.write(point);
        String POLYGON_WHERE_POINT = "select osm_id, ST_AsEWKB(way) as way, osm_id from planet_osm_polygon where ST_Contains(ST_GeometryFromText('" +
                pointS + "'," + SRID + "), way)";
        Polygon polygon = null;
        try {
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(POLYGON_WHERE_POINT);
            while (resultSet.next()){
                Geometry way = reader.read(resultSet.getBytes("way"));
                if (way.getGeometryType().equals(OsmDatabaseManager.Definitions.POLYGON)){
                    polygon = (Polygon) way;
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException | ParseException e){
            e.printStackTrace();
        }

        return polygon;
    }

    @Override
    public List<PolygonV2> getPolygonsFromBoundaryBox(NetworkFactory factory, Geometry geometry) {
        List<PolygonV2> result = new ArrayList<>();
        WKTWriter writer = new WKTWriter();
        getSRID();
        String poly = writer.write(geometry);
        String POLYGONS_INSIDE_BORDERS = "select planet_osm_polygon.osm_id as osm_id, ST_AsEWKB(way) as way, planet_osm_polygon.landuse as landuse, planet_osm_polygon.natural as natural from planet_osm_polygon where ST_Contains(ST_GeometryFromText('" +
                poly + "'," + SRID + "), way)";
        try {
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(POLYGONS_INSIDE_BORDERS);
            while(resultSet.next()){
                Polygon polygon = null;
                String natural;
                long osm_id;
                Geometry way = reader.read(resultSet.getBytes("way"));
                if (way.getGeometryType().equals(OsmDatabaseManager.Definitions.POLYGON)){
                    polygon = (Polygon) way;
                }
                if(polygon!=null){
                    natural = resultSet.getString("natural");
                    osm_id = resultSet.getLong("osm_id");
                    PolygonV2 polygonV2 = factory.createPolygon(Id.createPolygonId(osm_id), polygon.getExteriorRing().getCoordinates());
                    PolygonType polygonType = PolygonType.valueOfType(natural);
                    if (polygonType.getParam().equals(PolygonType.DEFAULT.getParam())){
                        String landuse = resultSet.getString("landuse");
                        polygonType = PolygonType.valueOfLanduse(landuse);
                    }
                    polygonV2.setType(polygonType);
                    result.add(polygonV2);
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException | ParseException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> getNaturalTypes(){
        List<String> list = new ArrayList<>();
        try {
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select planet_osm_polygon.natural from planet_osm_polygon where planet_osm_polygon.natural is not null ");
            while (resultSet.next()){
                list.add(resultSet.getString("natural"));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
