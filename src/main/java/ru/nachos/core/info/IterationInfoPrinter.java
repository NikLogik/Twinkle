package ru.nachos.core.info;

import com.opencsv.CSVWriter;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.web.models.ResponseDataContainer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class IterationInfoPrinter implements IterationPrinter {

//    static Logger logger = Logger.getLogger(IterationInfoPrinter.class);


    @Override
    public void info(Config config, InitialPreprocessingData data) {
        System.out.println(config.toString());
        System.out.println(data.toString());
        System.out.println("Config: " + config.hashCode() + System.lineSeparator() +
                "InitialData: " + data.hashCode());
    }

    public static void printResultData(ResponseDataContainer container){
        AtomicInteger count = new AtomicInteger(2);
//        logger.info("<======================== Print result data ===========================>");
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(new File("result_info_295_with_close_event.txt")));
            String[] header = new String[]{"iter","lon","lat"};
            writer.writeNext(header, false);
//            for (Map.Entry<Integer, ResponseData> entry : container.getAgents().entrySet()){
//                entry.getValue().getData().forEach(data-> writer.writeNext(new String[]{String.valueOf(entry.getKey()),
//                        String.valueOf(data.getX()), String.valueOf(data.getY())},false));
//            }
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
