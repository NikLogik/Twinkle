package ru.nachos.db;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nachos.db.repository.PolygonRepository;

import javax.persistence.NonUniqueResultException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Polygon> getPolygonsFromBoundaryBox(Geometry polygon) {
        Map<String, Polygon> result = new HashMap<>();
        WKTWriter writer = new WKTWriter();
        getSRID();
        String poly = writer.write(polygon);
        String POLYGONS_INSIDE_BORDERS = "select osm_id, ST_AsEWKB(way) as way, osm_id from planet_osm_polygon where ST_Contains(ST_GeometryFromText('" +
                poly + "'," + SRID + "), way)";
        try {
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(POLYGONS_INSIDE_BORDERS);
            while(resultSet.next()){
                Geometry way = reader.read(resultSet.getBytes("way"));
                if (way.getGeometryType().equals(OsmDatabaseManager.Definitions.POLYGON)){
                    result.put(resultSet.getString("osm_id"), (Polygon) way);
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException | ParseException e){
            e.printStackTrace();
        }
        return result;
    }
}
