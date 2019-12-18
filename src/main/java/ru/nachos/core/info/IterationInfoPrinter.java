package ru.nachos.core.info;

import org.apache.log4j.Logger;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseDataContainer;
import ru.nachos.web.models.lib.ResponseData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IterationInfoPrinter implements IterationPrinter {

    static Logger logger = Logger.getLogger(IterationInfoPrinter.class);


    @Override
    public void info(Config config, InitialPreprocessingData data) {
        System.out.println(config.toString());
        System.out.println(data.toString());
        System.out.println("Config: " + config.hashCode() + System.lineSeparator() +
                "InitialData: " + data.hashCode());
    }

    public static void printResultData(ResponseDataContainer container){
        logger.warn("Delete this method from FireModelRunner before deploy to server");
        for (Map.Entry<Integer, ResponseData> entry : container.getAgents().entrySet()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result_info_295_"+entry.getKey()+".txt")))) {
                message(entry.getKey());
                String header = "osm_id,lon,lat";
                writer.write(header);
                CoordinateJson[] data1 = entry.getValue().getData();
                for (int i=0; i<data1.length; i++){
                    writer.newLine();
                    writer.write(entry.getKey() + "," + data1[i].getX() + "," + data1[i].getY());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printResultData(int iterNum, LinkedList<AgentState> iterationList){
        logger.warn("Delete this method from ControllerImpl before deploy to server");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("iteration_info_"+iterNum+".txt")))){
            message(iterNum);
            String header = "osm_id,lon,lat";
            writer.write(header);
            for (AgentState state : iterationList){
                writer.newLine();
                writer.write(state.getAgent().toString() + "," + state.getCoordinate().x + "," + state.getCoordinate().y);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void message(int iterNum){
        logger.info("<======================== Print result data for iteration #"+iterNum+"===========================>");
    }

    @Override
    public void printGeometryTypes(List<String> polygons){
        System.out.println("<=============== Print natural types ====================>");
        Set<String> collect = new HashSet<>(polygons);
        String[] strings = collect.toArray(new String[0]);
            Arrays.stream(strings).forEach(System.out::println);
    }
}
