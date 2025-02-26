package git.niklogik.core.fire.algorithms;

import git.niklogik.calc.geo.Point3D;
import git.niklogik.calc.speed.AngleTangent;
import git.niklogik.calc.speed.ReliefSpeedRatio;
import git.niklogik.core.network.lib.ForestFuelType;
import git.niklogik.core.network.lib.Node;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import lombok.Setter;

import java.math.BigDecimal;

import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;

public class Rotermel implements FireSpreadCalculator {

    private double gamma;

    @Setter private ForestFuelType type;

    private BigDecimal windSpeed;

    @Override
    public BigDecimal calculateForDirection(BigDecimal fireSpeed, BigDecimal agentDirection, double windDirection) {
        var direction = toBigDecimal(windDirection).subtract(agentDirection);
        var albiniSpeed = albiniFormulaForWindSpeed(windSpeedOn6Meters());
        var A = 0.785 * albiniSpeed - 0.106 * Math.pow(albiniSpeed, 2);
        var resultSpeed = fireSpeed.doubleValue() * Math.exp(
            A * (Math.cos(Math.toRadians(direction.doubleValue())) - 1));
        return toBigDecimal(resultSpeed);
    }

    @Override
    public BigDecimal calculateForFuel(ForestFuelTypeDao forestFuelTypeDao, BigDecimal windSpeed) {
        double speed = calculateSpeedWithoutExternalConstraint(forestFuelTypeDao);
        this.windSpeed = windSpeed;
        double windK = windCoefficient();
        return toBigDecimal(speed * (1 + windK));
    }

    public double calculateSpeedWithoutExternalConstraint(ForestFuelType type) {
        this.type = type;
        this.gamma = (3.767 * (Math.pow(10, -4)) * type.getMiddleReserve())
                     / (type.getDepth() * Math.pow(type.getSpecificArea(), -0.8189));

        //потенциальная скорость реакции горения
        double potentialSpeedOfBurning = 0.168 * Math.pow(type.getSpecificArea(), 1.5)
                                         * Math.pow(
            (495 + 9.979 * Math.pow(10, -3) * Math.pow(type.getSpecificArea(), 1.5)), -1)
                                         * Math.pow(
            Math.pow((4.239 * Math.pow(type.getSpecificArea(), 0.1) - 7.27), -1), -1)
                                         * Math.exp(
            Math.pow((4.239 * Math.pow(type.getSpecificArea(), 0.1) - 7.27), -1 * (1 - gamma)));

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
        return 0.048 * type.getHeat() * type.getMineralConsistency() * type.getMiddleReserve()
               * coefficientSlowdownOfBurning * potentialSpeedOfBurning * heatSpread
               / ((type.getDensityForestFuel() + type.getDensityForestFuel() * type.getMineralMatter()) * heatBurning * gamma
                  * effectiveFuelDensity * Math.pow(type.getSpecificArea(), -0.8189));
    }

    /**
     * Thia method calculate coefficient of wind effect by the speed of fire spread
     *
     * @return
     */
    public double windCoefficient() {
        double k1 = Math.pow(gamma, -0.715 * Math.exp(-1.094 * Math.pow(10, -4) * type.getSpecificArea()));
        double speed = albiniFormulaForWindSpeed(windSpeedOn6Meters().doubleValue());
        double k2 = 7.47 * Math.exp((-0.06919 * Math.pow(type.getSpecificArea(), 0.55))
                                    * Math.pow((196.848 * speed), 0.0133 * Math.pow(type.getSpecificArea(), 0.54)));
        return k1 * k2;
    }

    public BigDecimal windSpeedOn6Meters() {
        return toBigDecimal(windSpeed.doubleValue() * Math.pow(((type.getTreeHeight() + 6) / 10.0), 0.28));
    }

    /**
     * This method calculate wind speed according to the Albini`s formula for the half of fire`s height
     *
     * @param windSpeed6meters
     * @return value wind coefficient
     */
    private double albiniFormulaForWindSpeed(double windSpeed6meters) {
        if (windSpeed6meters == 0.000) {
            windSpeed6meters = windSpeedOn6Meters().doubleValue();
        }
        double albiniSpeed = 0.31 * windSpeed6meters / (Math.sqrt(type.getCapacityDensityWood() * type.getTreeHeight())
                                                        * Math.log(
            (20 + 1.18 * type.getTreeHeight()) / (0.43 * type.getTreeHeight())));
        return albiniSpeed;
    }

    @Override
    public BigDecimal reliefCoefficient(Node from, Node to) {
        var fromPoint = new Point3D(from.getElevation(), from.getCoordinate());
        var toPoint = new Point3D(to.getElevation(), to.getCoordinate());
        return toBigDecimal(
            new ReliefSpeedRatio(type)
                .reliefRatio(new AngleTangent(fromPoint, toPoint))
        );
    }
}
