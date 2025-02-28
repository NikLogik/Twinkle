package git.niklogik.core.controller;

import git.niklogik.core.config.lib.Config;
import git.niklogik.core.controller.lib.InitialPreprocessingData;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.algorithms.FireSpreadCalculator;
import git.niklogik.core.fire.algorithms.Rotermel;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.network.NetworkUtils;
import git.niklogik.core.network.lib.Network;
import git.niklogik.core.weather.Weather;
import git.niklogik.core.weather.WeatherUtils;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import static java.lang.String.format;

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
        this.calculator = new Rotermel(this.weather);
        this.transformation = createTransformation(config.getSrid());
        this.reTransformation = createReTransformation(config.getSrid());
    }

    private MathTransform createReTransformation(int sourceSrid) {
        CoordinateReferenceSystem fromSystem;
        CoordinateReferenceSystem toSystem;
        MathTransform math = null;
        try {
            fromSystem = CRS.decode("EPSG:" + osmDatabaseSRID, true);
            toSystem = CRS.decode(format("EPSG:%s", sourceSrid), true);
            math = CRS.findMathTransform(fromSystem, toSystem);
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        return math;
    }

    private MathTransform createTransformation(int sourceSrid) {
        CoordinateReferenceSystem fromSystem;
        CoordinateReferenceSystem toSystem;
        MathTransform math = null;
        try {
            fromSystem = CRS.decode(format("EPSG:%s", sourceSrid), true);
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
