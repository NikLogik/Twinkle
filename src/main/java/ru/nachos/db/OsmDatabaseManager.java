package ru.nachos.db;

import org.postgresql.PGConnection;
import org.postgresql.geometric.*;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class OsmDatabaseManager {

    @Autowired
    private DataSource dataSource;
    private Connection connection;

    public Connection getConnection() throws SQLException{
        connection = dataSource.getConnection();
        if (connection.isWrapperFor(PGConnection.class)){
            PGConnection unwrap = connection.unwrap(PGConnection.class);
            unwrap.addDataType("geometry", PGobject.class);
            unwrap.addDataType("box", PGbox.class);
            unwrap.addDataType("point", PGpoint.class);
            unwrap.addDataType("polygon", PGpolygon.class);
            unwrap.addDataType("line", PGline.class);
            unwrap.addDataType("road", PGpath.class);
        }
        return connection;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static class Definitions{
        //types
        public static final String POLYGON = "Polygon";
        public static final String LINE_STRING = "LineString";
        public static final String POINT = "Point";
    }
}
