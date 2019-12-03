package ru.nachos.core.fire.algorithms;

import com.vividsolutions.jts.geom.Coordinate;
import org.apache.commons.math.util.MathUtils;
import ru.nachos.core.fire.TwinkleUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentState;
import ru.nachos.core.network.NetworkUtils;
import ru.nachos.core.network.lib.ForestFuelType;
import ru.nachos.core.network.lib.Network;

public class Rotermel implements FireSpreadCalculator {

    private Network network;

    private double gamma;
    private ForestFuelType type;
    private double windSpeed;
    private Agent agent;

    public Rotermel(Network network) {
        this.network = network;
    }

    @Override
    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public void calculateSpeedOfSpreadWithArbitraryDirection(double fireSpeed, Agent agent) {
        double direction = agent.getDirection();
        double albiniSpeed = albiniFormulaForWindSpeed(windSpeedOn6Meters());
        double A = 0.785 * albiniSpeed - 0.106 * Math.pow(albiniSpeed, 2);
        fireSpeed = fireSpeed * Math.exp(A * (Math.cos(Math.toRadians(direction)) - 1));
        TwinkleUtils.changeSpeed(agent, fireSpeed);
    }

    @Override
    public void calculateSpeedOfSpreadWithArbitraryDirectionV2(double fireSpeed, Agent agent, double windDirection) {
        double direction = agent.getDirection() - windDirection;
        double albiniSpeed = albiniFormulaForWindSpeed(windSpeedOn6Meters());
        double A = 0.785 * albiniSpeed - 0.106 * Math.pow(albiniSpeed, 2);
        fireSpeed = fireSpeed * Math.exp(A * (Math.cos(Math.toRadians(direction)) - 1));
        TwinkleUtils.changeSpeed(agent, fireSpeed);
    }

    @Override
    public double calculateSpeedOfSpreadWithConstraint(double fireSpeed, double windSpeed, boolean reliefData) {
        this.windSpeed = windSpeed;
        double windK = windCoefficient();
        double reliefK = 0.0;
        if (reliefData) {
            reliefK = reliefCoefficient(agent.getCoordinate(), agent.getLastState());
        }
        return fireSpeed * (1 + windK + reliefK);
    }

    @Override
    public double calculateSpeedWithoutExternalConstraint(ForestFuelType type) {
        this.type = type;
        this.gamma = (3.767 * (Math.pow(10, -4)) * type.getMiddleReserve())
                / (type.getDepth() * Math.pow(type.getSpecificArea(), -0.8189));

        //потенциальная скорость реакции горения
        double potentialSpeedOfBurning = 0.168 * Math.pow(type.getSpecificArea(), 1.5)
                * Math.pow((495 + 9.979 * Math.pow(10, -3) * Math.pow(type.getSpecificArea(), 1.5)), -1)
                * Math.pow(Math.pow((4.239 * Math.pow(type.getSpecificArea(), 0.1) - 7.27), -1), -1)
                * Math.exp(Math.pow((4.239 * Math.pow(type.getSpecificArea(), 0.1) - 7.27), -1 * (1 - gamma)));

        //доля теплового потока
        double heatSpread = Math.pow((192 + 0.079 * type.getSpecificArea()), -1)
                * Math.exp((0.792 + 0.376 * Math.pow(type.getSpecificArea(), 0.5))
                * (gamma * 8.858 * Math.pow(type.getSpecificArea(), -0.8189) + 0.1));

        //эффективная плотность горючего
        double effectiveFuelDensity = Math.pow(Math.E, -452.759 / type.getSpecificArea());

        //коэффициент замедления скорости сгорания
        double coefficientSlowdownOfBurning = (1 - 2.59 * type.getMoisture()) / type.getMaxMoisture()
                + 5.11 * Math.pow((type.getMoisture() / type.getMaxMoisture()), 2)
                - 3.52 * Math.pow((type.getMoisture() / type.getMaxMoisture()), 3);

        //теплота воспламенения
        double heatBurning = 250 + 1116 * type.getMoisture();

        //скорость распространения фронта пожара
        double speedOfFireSpread = 0.048 * type.getHeat() * type.getMineralConsistency() * type.getMiddleReserve()
                * coefficientSlowdownOfBurning * potentialSpeedOfBurning * heatSpread
                / ((type.getDensityForestFuel() + type.getDensityForestFuel() * type.getMineralMatter()) * heatBurning * gamma
                * effectiveFuelDensity * Math.pow(type.getSpecificArea(), -0.8189));
        return speedOfFireSpread;
    }

    /**
     * Thia method calculate coefficient of wind effect by the speed of fire spread
     *
     * @return
     */
    private double windCoefficient() {
        double k1 = Math.pow(gamma, -0.715 * Math.exp(-1.094 * Math.pow(10, -4) * type.getSpecificArea()));
        double speed = albiniFormulaForWindSpeed(windSpeedOn6Meters());
        double k2 = 7.47 * Math.exp((-0.06919 * Math.pow(type.getSpecificArea(), 0.55))
                * Math.pow((196.848 * speed), 0.0133 * Math.pow(type.getSpecificArea(), 0.54)));
        return k1 * k2;
    }

    private double windSpeedOn6Meters(){
        return windSpeed * Math.pow(((type.getTreeHeight() + 6)/10.0), 0.28);
    }

    /**
     * This method calculate wind speed according to the Albini`s formula for the half of fire`s height
     * @param windSpeed6meters

     * @return value wind coefficient
     */
    private double albiniFormulaForWindSpeed(double windSpeed6meters){
        if (windSpeed6meters == 0.000){
            windSpeed6meters = windSpeedOn6Meters();
        }
        double albiniSpeed = 0.31 * windSpeed6meters / (Math.sqrt(type.getCapacityDensityWood()* type.getTreeHeight())
                                    * Math.log((20 + 1.18 * type.getTreeHeight()) / (0.43 * type.getTreeHeight())));
        return albiniSpeed;
    }

    /**
     * This method calculate relief constraint for the speed of fire spread
     * @param coord - current coordinates of agent
     * @param lastState - state of agent on the previous iteration
     * @return value relief coefficient
     */
    private double reliefCoefficient(Coordinate coord, AgentState lastState){
        int height1 = NetworkUtils.getHeightPolygon(network.getPolygones(), NetworkUtils.findPolygonByAgentCoords(network, lastState.getCoord()));
        int height2 = NetworkUtils.getHeightPolygon(network.getPolygones(), NetworkUtils.findPolygonByAgentCoords(network, coord));
        int deltaHeight = height2 - height1;

        double[] p1 = new double[]{lastState.getCoord().x, lastState.getCoord().y};
        double[] p2 = new double[]{coord.x, coord.y};
        int distance = (int) MathUtils.distance1(p1, p2);

        double grade = deltaHeight / distance;

        double betta = 8.858 * gamma * Math.pow(type.getSpecificArea(), -0.8189);

        return 5.275 * Math.pow(betta, -0.3) * Math.pow(grade, 2);
    }
}
