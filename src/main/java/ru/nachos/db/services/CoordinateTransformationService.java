package ru.nachos.db.services;

import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoordinateTransformationService {

    @Value("${app.client.geography.srid:4326}")
    private int clientSRID;
    @Value("${app.database.osm.srid:3857}")
    private int osmDatabaseSRID;
    @Value("${app.database.fires.srid:4326}")
    private int fireDatabaseSRID;
    private final String EPSG = "EPSG:";

    public MathTransform getMathTransformation(TransformDirection direction){
        String fromSystem;
        String toSystem;
        switch (direction){
            case client_to_osmDB:
                fromSystem = EPSG + clientSRID;
                toSystem = EPSG + osmDatabaseSRID;
                break;
            case osmDB_to_client:
                fromSystem = EPSG + osmDatabaseSRID;
                toSystem = EPSG + clientSRID;
                break;
            case osmDB_to_fireDB:
                fromSystem = EPSG + osmDatabaseSRID;
                toSystem = EPSG + fireDatabaseSRID;
                break;
            case fireDB_to_osmDB:
                fromSystem = EPSG + fireDatabaseSRID;
                toSystem = EPSG + osmDatabaseSRID;
                break;
            default:
                fromSystem = "EPSG:4326";
                toSystem = "EPSG:4326";
        }
        MathTransform mathTransform = null;
        try{
            CoordinateReferenceSystem from = CRS.decode(fromSystem, true);
            CoordinateReferenceSystem to = CRS.decode(toSystem, true);
            mathTransform = CRS.findMathTransform(from, to);
        } catch (FactoryException ex) {
            ex.printStackTrace();
        }
        return mathTransform;
    }

    public int getClientSRID() {
        return clientSRID;
    }

    public int getOsmDatabaseSRID() {
        return osmDatabaseSRID;
    }

    public int getFireDatabaseSRID() {
        return fireDatabaseSRID;
    }

    public enum TransformDirection{
        client_to_osmDB,
        osmDB_to_client,
        osmDB_to_fireDB,
        fireDB_to_osmDB
    }
}
