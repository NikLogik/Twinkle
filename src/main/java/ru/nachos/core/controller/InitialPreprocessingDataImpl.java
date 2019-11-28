package ru.nachos.core.controller;

import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.springframework.stereotype.Component;
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
import ru.nachos.db.repository.PolygonRepository;

@Component
public class InitialPreprocessingDataImpl implements InitialPreprocessingData {

    private Config config;
    private Fire fire;
    private Weather weather;
    private Network network;
    private FireSpreadCalculator calculator;
    private MathTransform transformation;
    private PolygonRepository repository;

    public InitialPreprocessingDataImpl create(Config config){
        this.config = config;
        this.fire = FireUtils.createFire(this.config);
        this.network = NetworkUtils.createNetwork();
        this.weather = WeatherUtils.createWeather(this.config);
        this.calculator = FireSpreadCalculatorFactory.getCalculator(config.getCalculator(), this.network, this.weather);
        this.repository = NetworkUtils.getRepository();
        this.transformation = createTransformation(config.getSrid());
        return this;
    }

    private MathTransform createTransformation(String sourceSrid) {
        int srid = repository.getSRID();
        CoordinateReferenceSystem fromSystem = null;
        CoordinateReferenceSystem toSystem = null;
        MathTransform math = null;
        try {
            fromSystem = CRS.decode(sourceSrid);
            toSystem = CRS.decode("EPSG:" + srid);
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
    public InitialPreprocessingData preprocessing(){ return InitialPreprocessingDataUtils.loadInitialData(this); }
    @Override
    public MathTransform getTransformation() {
        return transformation;
    }
}
