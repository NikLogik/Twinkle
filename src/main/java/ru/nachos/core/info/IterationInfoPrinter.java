package ru.nachos.core.info;

import com.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.web.models.lib.AgentIterData;
import ru.nachos.web.models.lib.ResultData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IterationInfoPrinter implements IterationInfo {

    static Logger logger = Logger.getLogger(IterationInfoPrinter.class);


    @Override
    public void info(Config config, InitialPreprocessingData data) {
        System.out.println(config.toString());
        System.out.println(data.toString());
        System.out.println("Config: " + config.hashCode() + System.lineSeparator() +
                "InitialData: " + data.hashCode());
    }

    public static void printResultData(ResultData resultData){
        AtomicInteger count = new AtomicInteger(2);
        logger.info("<======================== Print result data ===========================>");
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(new File("result_info_295_5.txt")));
            String[] header = new String[]{"iter","id_agent","lon","lat"};
            writer.writeNext(header, false);
            for (Map.Entry<Integer, List<AgentIterData>> entry : resultData.getAgents().entrySet()){
                entry.getValue().forEach(data-> writer.writeNext(new String[]{String.valueOf(entry.getKey()), data.getAgentId(),
                        String.valueOf(data.getCordinates().x), String.valueOf(data.getCordinates().y)},false));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printGeometryTipes(List<String> polygons){
        System.out.println("<=============== Print natural types ====================>");
        Set<String> collect = new HashSet<>(polygons);
        String[] strings = collect.toArray(new String[0]);
            Arrays.stream(strings).forEach(System.out::println);
    }
}
