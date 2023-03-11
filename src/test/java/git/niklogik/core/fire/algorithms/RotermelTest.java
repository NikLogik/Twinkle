package git.niklogik.core.fire.algorithms;

import git.niklogik.calc.geo.Point3D;
import git.niklogik.calc.speed.AngleTangent;
import git.niklogik.calc.speed.ReliefSpeedRatio;
import git.niklogik.core.Id;
import git.niklogik.core.network.NodeImpl;
import git.niklogik.core.network.lib.Node;
import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author niklogik
 */
class RotermelTest {

    private double fromElevation;
    private double toElevation;
    private Coordinate fromCoordinate;
    private Coordinate toCoordinate;
    private ForestFuelTypeDao fuelTypeDao;

    @BeforeEach
    void beforeEach() {
        fuelTypeDao = new ForestFuelTypeDao();
        fuelTypeDao.setSpecificArea(23);
        fuelTypeDao.setDepth(1.23);
        fuelTypeDao.setMiddleReserve(3.21);

        fromElevation = 12.1;
        toElevation = 15.1;
        fromCoordinate = new Coordinate(20.0, 20.0);
        toCoordinate = new Coordinate(30.0, 30.0);
    }

    @Test
    void reliefCoefficientRatioTest() {
        NodeImpl from = new NodeImpl(Id.create(1, Node.class), fromElevation, fromCoordinate);
        NodeImpl to = new NodeImpl(Id.create(1, Node.class), toElevation, toCoordinate);

        Rotermel rotermel = new Rotermel();
        rotermel.calculateSpeedWithoutExternalConstraint(fuelTypeDao);
        rotermel.setType(fuelTypeDao);

        double reliefCoefficient = rotermel.reliefCoefficient(from, to);

        ReliefSpeedRatio reliefSpeedRatio = new ReliefSpeedRatio(fuelTypeDao);
        Point3D fromPoint = new Point3D(fromElevation, fromCoordinate);
        Point3D toPoint = new Point3D(toElevation, toCoordinate);
        Double reliefRatio = reliefSpeedRatio.reliefRatio(new AngleTangent(fromPoint, toPoint));

        assertThat(reliefCoefficient).isEqualTo(reliefRatio);
    }
}