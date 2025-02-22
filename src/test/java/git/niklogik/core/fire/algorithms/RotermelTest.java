package git.niklogik.core.fire.algorithms;

import git.niklogik.core.network.NodeImpl;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import static git.niklogik.core.utils.BigDecimalUtils.toBigDecimal;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author niklogik
 */
class RotermelTest {

    private double fromElevation;
    private double toElevation;
    private Coordinate fromCoordinate;
    private Coordinate toCoordinate;
    private Rotermel rotermel;

    @BeforeEach
    void beforeEach() {
        var fuelType = new ForestFuelTypeDao();
        fuelType.setSpecificArea(23);
        fuelType.setDepth(1.23);
        fuelType.setMiddleReserve(3.21);

        fromElevation = 12.1;
        toElevation = 15.1;
        fromCoordinate = new Coordinate(20.0, 20.0);
        toCoordinate = new Coordinate(30.0, 30.0);

        rotermel = new Rotermel();
        rotermel.setType(fuelType);
    }

    @Test
    void reliefCoefficientRatioTest() {
        NodeImpl from = new NodeImpl(randomUUID(), fromElevation, fromCoordinate);
        NodeImpl to = new NodeImpl(randomUUID(), toElevation, toCoordinate);

        var actual = rotermel.reliefCoefficient(from, to);

        var expected = toBigDecimal(0.985);

        assertThat(actual).isEqualTo(expected);
    }
}