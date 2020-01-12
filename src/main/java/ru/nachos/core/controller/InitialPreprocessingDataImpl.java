package ru.nachos.core.controller;

import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.controller.lib.InitialPreprocessingData;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.algorithms.FireSpreadCalculator;
import ru.nachos.core.fire.algorithms.FireSpreadCalculatorFactory;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.Network;
import ru.nachos.core.weather.WeatherUtils;
import ru.nachos.core.weather.lib.Weather;

public class InitialPreprocessingDataImpl implements InitialPreprocessingData {

    private Config config;
    private Fire fire;
    private Weather weather;
    private Network network;
    private FireSpreadCalculator calculator;
    private MathTransform transformation;
    private MathTransform reTransformation;
    private int osmDatabaseSRID;

    public InitialPreprocessingDataImpl(Config config, int osmDatabaseSRID){
        this.config = config;
        this.osmDatabaseSRID = osmDatabaseSRID;
        this.fire = FireUtils.createFire(this.config);
        this.network = NetworkUtils.createNetwork();
        this.weather = WeatherUtils.createWeather(this.config);
        this.calculator = FireSpreadCalculatorFactory.getCalculator(config.getCalculator(), this.network);
        this.transformation = createTransformation(config.getSrid());
        this.reTransformation = createReTransformation(config.getSrid());
    }

    private MathTransform createReTransformation(String sourceSrid) {
        CoordinateReferenceSystem fromSystem;
        CoordinateReferenceSystem toSystem;
        MathTransform math = null;
        try {
            fromSystem = CRS.decode("EPSG:" + osmDatabaseSRID, true);
            toSystem = CRS.decode(sourceSrid, true);
            math = CRS.findMathTransform(fromSystem, toSystem);
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        return math;
    }

    private MathTransform createTransformation(String sourceSrid) {
        CoordinateReferenceSystem fromSystem;
        CoordinateReferenceSystem toSystem;
        MathTransform math = null;
        try {
            fromSystem = CRS.decode(sourceSrid, true);
            toSystem = CRS.decode("EPSG:" + osmDatabaseSRID, true);
            math = CRS.findMathTransform(fromSystem, toSystem);
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        return math;
    }

    @Override
    public Config getConfig() { return config; }

    @Override
    public Fire getFire() { return fire; }

    void setFire(Fire fire) { this.fire = fire; }
    @Override
    public Weather getWeather() { return weather; }

    @Override
    public Network getNetwork() { return network; }
    @Override
    public FireSpreadCalculator getCalculator() {return calculator; }
    @Override
    public MathTransform getTransformation() {
        return transformation;
    }
    @Override
    public MathTransform getReTransformation() {
        return reTransformation;
    }

    void setConfig(Config config) { this.config = config; }

    void setWeather(Weather weather) {
        this.weather = weather;
    }

    void setNetwork(Network network) {
        this.network = network;
    }

    public void setCalculator(FireSpreadCalculator calculator) {
        this.calculator = calculator;
    }

    void setTransformation(MathTransform transformation) {
        this.transformation = transformation;
    }

    void setReTransformation(MathTransform reTransformation) {
        this.reTransformation = reTransformation;
    }

    @Override
    public void setSRID(int srid) { this.osmDatabaseSRID = srid; }
    @Override
    public int getOsmDatabaseSRID() { return osmDatabaseSRID; }

    @Override
    public String toString() {
        return "InitialPreprocessingDataImpl{" + System.lineSeparator() +
                ", fire=" + fire.toString() + System.lineSeparator() +
                ", network=" + network.toString() + System.lineSeparator() +
                ", calculator=" + calculator.toString() + System.lineSeparator() +
                ", transformation=" + transformation.toString() + System.lineSeparator() +
                ", reTransformation=" + reTransformation.toString() + System.lineSeparator() +
                '}';
    }
}
