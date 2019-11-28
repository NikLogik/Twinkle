package ru.nachos.core.network.io;

import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class OsmReliefMapReader {

    private static String source;
    private static Map<String, MultiPolygon> dataMulti = new HashMap<>();
    private static Map<String, MultiLineString> dataPoly = new HashMap<>();

    public OsmReliefMapReader(String src){
        source = src;
    }

    public static void run() throws IOException {

        MultiPolygon multiPolygon;
        MultiLineString multiLineString;

        File file = new File(source);
        Map<String, Object> map = new HashMap<>();
        map.put("url", file.toURI().toURL());

        DataStore dataStore = DataStoreFinder.getDataStore(map);
        String typeName = dataStore.getTypeNames()[0];

        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        Filter filter = Filter.INCLUDE;

        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);

        try(FeatureIterator<SimpleFeature> features = collection.features()){
            int inc = 0;
            while(features.hasNext()){
                SimpleFeature feature = features.next();
                GeometryAttribute defaultGeometryProperty = feature.getDefaultGeometryProperty();

                String featureId;
                if (defaultGeometryProperty.getValue() instanceof MultiPolygon){
                    try {
                        featureId = feature.getAttribute("id").toString();
                    } catch (NullPointerException e){
                        featureId = String.valueOf(inc);
                        inc++;
                    }
                    multiPolygon = (MultiPolygon) defaultGeometryProperty.getValue();
                    dataMulti.put(featureId, multiPolygon);
                }
                if (defaultGeometryProperty.getValue() instanceof MultiLineString){
                    try {
                        featureId = feature.getAttribute("id").toString();
                    } catch (NullPointerException e){
                        featureId = String.valueOf(inc);
                        inc++;
                    }
                    multiLineString = (MultiLineString) defaultGeometryProperty.getValue();
                    dataPoly.put(featureId, multiLineString);
                }
            }
        }
    }

    /**
     * Just for running example loading .shp file
     * @param args
     * @deprecated
     */
    public static void main(String[] args) {
        OsmReliefMapReader reader = new OsmReliefMapReader("src/main/resources/contour.shp");
        try {
            OsmReliefMapReader.run();
            System.out.println(dataPoly);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
