package ru.nachos.db;

import org.postgresql.PGConnection;
import org.postgresql.geometric.*;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class OsmDatabaseManager {

    @Autowired
    private DataSource dataSource;
    private Connection connection;

    private void init(){
        dataSource = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .username("model")
                .password("Bc9Q_13k$h![lJx7oW1vc")
                .url("jdbc:postgresql://185.124.188.247:5432/model")
                .build();
    }

    public Connection getConnection() throws SQLException{
        init();
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

    public DataSource getDataSource() {
        return dataSource;
    }

    static class Definitions{
        //types
        public static final String POLYGON = "Polygon";
        public static final String LINE_STRING = "LineString";
    }
}
