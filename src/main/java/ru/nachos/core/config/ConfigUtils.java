package ru.nachos.core.config;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.config.lib.Config;

public class ConfigUtils {

    public static Config createConfig(){
        return new ConfigImpl();
    }

    public static void changeFireCenterCoordinates(Config config, Coordinate newCenter){
        ConfigImpl configImpl = (ConfigImpl) config;
        configImpl.setFireCenterCoordinate(newCenter);
    }

    public static class ConfigBuilder{

        private ConfigImpl config;

        public ConfigBuilder(Config config){
            this.config = (ConfigImpl) config;
        }
        public ConfigBuilder setLastIteration(int lastIteration){
            config.setLastIteration(lastIteration);
            return this;
        }
        public ConfigBuilder setIterationStepTime(int iterationStepTime){
            config.setStepTimeAmount(iterationStepTime);
            return this;
        }
        public ConfigBuilder setStartTime(int startTime){
            config.setStartTime(startTime);
            return this;
        }
        public ConfigBuilder setEndTime(int endTime){
            config.setEndTime(endTime);
            return this;
        }
        public ConfigBuilder setFuelType(int type){
            for (Config.ForestFuelType type1 : Config.ForestFuelType.values()){
                if (type1.getValue() == type) {
                    config.setFuelType(type1);
                    break;
                }
            }
            return this;
        }
        public ConfigBuilder setFireCenterCoordinate(Coordinate coordinate){
            config.setFireCenterCoordinate(coordinate);
            return this;
        }
        public ConfigBuilder setFireAgentDIstance(int distance){
            config.setFireAgentsDistance(distance);
            return this;
        }
        public ConfigBuilder setFireClass(int fireClass){
            config.setFireClass(Config.FirePowerClassification.values()[fireClass]);
            return this;
        }
        public ConfigBuilder setWindSpeed(double speed){
            config.setWindSpeed(speed);
            return this;
        }
        public ConfigBuilder setWindDirection(double windDirection){
            config.setWindDirection(windDirection);
            return this;
        }
        public Config build(){
            return this.config;
        }
    }
}
