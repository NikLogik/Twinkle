package ru.nachos.core.info;

import com.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import ru.nachos.db.entities.fire.ContourLine;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

public class ReliefReader {

    private static Logger logger = Logger.getLogger(ReliefReader.class);

    private static String path = "results/Krym_25m.csv";
    private static TreeMap<Long, ContourLine> relief = new TreeMap<>();

    public static TreeMap<Long, ContourLine> getRelief(){
        logger.warn("Start loading contour lines from " + path);
        try{
            CSVReader reader = new CSVReader(new FileReader(path), ',');
            Iterator<String[]> iterator = reader.iterator();
            ContourLine line;
            WKTReader wktReader = new WKTReader();
            iterator.next();
            while (iterator.hasNext()){
                String[] next = iterator.next();
                double elev = Double.parseDouble(next[2]);
                LineString lineString = (LineString) wktReader.read(next[0]);
                lineString.setSRID(3857);
                line = new ContourLine(elev, lineString);
                relief.put(Long.parseLong(next[1]), line);
            }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
        logger.warn("Loaded contour lines: " + relief.size());
        return relief;
    }
}
