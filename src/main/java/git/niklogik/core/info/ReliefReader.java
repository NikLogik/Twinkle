package git.niklogik.core.info;

import com.opencsv.CSVReader;

import git.niklogik.db.entities.fire.ContourLine;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

public class ReliefReader {

    private static final Logger logger = LoggerFactory.getLogger(ReliefReader.class);

    private static final TreeMap<Long, ContourLine> relief = new TreeMap<>();

    public static TreeMap<Long, ContourLine> getRelief() {
        String path = "results/Krym_25m.csv";
        logger.warn("Start loading contour lines from {}", path);
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            Iterator<String[]> iterator = reader.iterator();
            ContourLine line;
            WKTReader wktReader = new WKTReader();
            iterator.next();
            while (iterator.hasNext()) {
                String[] next = iterator.next();
                double elev = Double.parseDouble(next[2]);
                LineString lineString = (LineString) wktReader.read(next[0]);
                lineString.setSRID(3857);
                line = new ContourLine(elev, lineString);
                relief.put(Long.parseLong(next[1]), line);
            }
        } catch (IOException | ParseException e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("Loaded contour lines: {}", relief.size());
        return relief;
    }
}
