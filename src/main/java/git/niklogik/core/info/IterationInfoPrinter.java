package git.niklogik.core.info;


import git.niklogik.core.config.lib.Config;
import git.niklogik.core.fire.lib.AgentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import git.niklogik.core.controller.lib.InitialPreprocessingData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IterationInfoPrinter implements IterationPrinter {

    static Logger logger = LoggerFactory.getLogger(IterationInfoPrinter.class);


    @Override
    public void info(Config config, InitialPreprocessingData data) {
        System.out.println(config.toString());
        System.out.println(data.toString());
        System.out.println("Config: " + config.hashCode() + System.lineSeparator() +
                "InitialData: " + data.hashCode());
    }

    public static void printResultData(int iterNum, LinkedList<AgentState> iterationList){
        logger.warn("Delete this method from ControllerImpl before deploy to server");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("results/iteration_info_"+iterNum+".txt")))){
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
