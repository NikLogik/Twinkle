package ru.nachos.core.info;

import com.opencsv.CSVReader;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.apache.log4j.Logger;
import ru.nachos.db.model.fire.ContourLine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

public class ReliefReader {

    private static Logger logger = Logger.getLogger(ReliefReader.class);

    private static String path = "results/Krym_20-12-2019.csv";
    private static TreeMap<Long, ContourLine> relief = new TreeMap<>();

    public static TreeMap<Long, ContourLine> getRelief(){
        logger.warn("Start loading contour lines from " + path);
        try{
            CSVReader reader = new CSVReader(new FileReader(new File(path)), ',');
            Iterator<String[]> iterator = reader.iterator();
            ContourLine line;
            WKTReader wktReader = new WKTReader();
            iterator.next();
            while (iterator.hasNext()){
                String[] next = iterator.next();
                line = new ContourLine(Double.parseDouble(next[2]), (LineString) wktReader.read(next[0]));
                relief.put(Long.parseLong(next[1]), line);
            }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
        logger.warn("Loaded contour lines: " + relief.size());
        return relief;
    }
}
