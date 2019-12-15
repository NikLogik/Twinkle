package ru.nachos.db;

import com.opencsv.CSVWriter;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nachos.core.Id;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.NetworkFactory;
import ru.nachos.core.network.lib.PolygonV2;
import ru.nachos.core.utils.PolygonType;
import ru.nachos.db.repository.PolygonRepository;

import javax.persistence.NonUniqueResultException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class PolygonRepositoryImpl implements PolygonRepository {

//    private static Logger logger = Logger.getLogger(PolygonRepositoryImpl.class);

    @Autowired
    OsmDatabaseManager manager;
    private GeometryFactory geoFactory = new GeometryFactory();
    private static WKBReader reader = new WKBReader();
    private static  WKTWriter writer = new WKTWriter(2);
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
            statement.close();
            connection.close();
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

    public Coordinate getIntersectionPoint(Coordinate[] coordinate, PolygonV2 polygonV2){
        LineString lineString = geoFactory.createLineString(coordinate);
        String wLine = writer.write(lineString);
        String wPolygon = writer.write(polygonV2);
        Geometry geometry = null;
        try {
            Connection connection = manager.getConnection();
            PreparedStatement statement = connection.prepareStatement("select st_asewkb(st_intersection(st_geomfromtext('"+ wLine
                                                +"'),st_boundary(st_geomfromtext('"+ wPolygon +"')))) as geo");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                byte[] geos = resultSet.getBytes("geo");
                geometry = reader.read(geos);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert geometry != null;
        return geometry.getCoordinate();
    }

    public void getAllPolygons(){
        try {
//            logger.info("Start loading all polygons from Database");
            List<String> list = new ArrayList<>();
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select ST_AsEWKB(way) as way from planet_osm_polygon");
//            logger.info("Finished loading all polygons from database");
            Geometry geom;
            while (resultSet.next()){
                geom = reader.read(resultSet.getBytes("way"));
                list.add(geom.toString());
            }
            CSVWriter writer = new CSVWriter(new FileWriter(new File("polys.txt")));
            list.forEach(way->writer.writeNext(list.toArray(new String[0]),false));
        } catch (SQLException | IOException | ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public PolygonV2 getPolygonByCoordinate(NetworkFactory netFactory, Coordinate coordinate) {
        Point point = this.geoFactory.createPoint(coordinate);
        String pointS = writer.write(point);
//        logger.info("Get geometry by coordinate "+pointS);
        String POLYGON_WHERE_POINT = "select planet_osm_polygon.osm_id as osm_id, ST_AsEWKB(way) as way, planet_osm_polygon.landuse as landuse, planet_osm_polygon.natural as natural, planet_osm_polygon.water as water, planet_osm_polygon,waterway as waterway from planet_osm_polygon where ST_Contains(ST_GeometryFromText('" +
                pointS + "'," + SRID + "), way)";
        PolygonV2 polygon = null;
        long counter = 100000;
        try {
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(POLYGON_WHERE_POINT);
            Id<PolygonV2> osm_id;
            while (resultSet.next()){
                Geometry way = reader.read(resultSet.getBytes("way"));
                if (way.getGeometryType().equals(OsmDatabaseManager.Definitions.POLYGON)){
                    polygon = (PolygonV2) way;
                }
            }
            if(polygon!=null){
                String natural = resultSet.getString("natural");
                long id = resultSet.getLong("osm_id");
                if (NetworkUtils.networkCashe.containsValue(id)){
                    id += counter;
                    osm_id = Id.createPolygonId(id + ":duplicate" + counter);
                    ++counter;
                } else {
                    osm_id = Id.createPolygonId(id);
                    NetworkUtils.networkCashe.put(osm_id, id);
                }
                polygon = netFactory.createPolygon(osm_id, polygon.getExteriorRing().getCoordinates());
                PolygonType polygonType = PolygonType.valueOfType(natural);
                String water = resultSet.getString("water");
                if (water != null && !water.isEmpty()){
                    polygonType = PolygonType.WATER;
                }
                String waterway = resultSet.getString("waterway");
                if (waterway != null && !waterway.isEmpty()){
                    polygonType = PolygonType.WATER;
                }
                if (polygonType.getParam().equals(PolygonType.DEFAULT.getParam())){
                    String landuse = resultSet.getString("landuse");
                    polygonType = PolygonType.valueOfLanduse(landuse);
                }
                polygon.setType(polygonType);
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

//        logger.info("Start loading geometries from Database");

        List<PolygonV2> result = new ArrayList<>();
        getSRID();
        String poly = writer.write(geometry);
        String POLYGONS_INSIDE_BORDERS = "select planet_osm_polygon.osm_id as osm_id, ST_AsEWKB(way) as way, planet_osm_polygon.landuse as landuse, planet_osm_polygon.natural as natural, planet_osm_polygon.water as water, planet_osm_polygon,waterway as waterway from planet_osm_polygon where ST_Contains(ST_GeometryFromText('" +
                poly + "'," + SRID + "), way)";
        try {
            Connection connection = manager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(POLYGONS_INSIDE_BORDERS);
            List<Long> ids = new LinkedList<>();
            long counter = 100000;
            while(resultSet.next()){
                Polygon polygon = null;
                String natural;
                Id<PolygonV2> osm_id = null;
                Geometry way = reader.read(resultSet.getBytes("way"));
                if (way.getGeometryType().equals(OsmDatabaseManager.Definitions.POLYGON)){
                    polygon = (Polygon) way;
                }
                if(polygon!=null){
                    natural = resultSet.getString("natural");
                    long id = resultSet.getLong("osm_id");
                    if (ids.contains(id)){
                        id += counter;
                        osm_id = Id.createPolygonId(id + ":duplicate" + counter);
                        ++counter;
                    } else {
                        osm_id = Id.createPolygonId(id);
                        ids.add(id);
                    }
                    PolygonV2 polygonV2 = factory.createPolygon(osm_id, polygon.getExteriorRing().getCoordinates());
                    PolygonType polygonType = PolygonType.valueOfType(natural);
                    String water = resultSet.getString("water");
                    if (water != null && !water.isEmpty()){
                        polygonType = PolygonType.WATER;
                    }
                    String waterway = resultSet.getString("waterway");
                    if (waterway != null && !waterway.isEmpty()){
                        polygonType = PolygonType.WATER;
                    }
                    if (polygonType.getParam().equals(PolygonType.DEFAULT.getParam())){
                        String landuse = resultSet.getString("landuse");
                        polygonType = PolygonType.valueOfLanduse(landuse);
                    }
                    polygonV2.setType(polygonType);
                    result.add(polygonV2);
                }
            }
//            logger.info("Finished loading geometries from Database: " + result.size());
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
