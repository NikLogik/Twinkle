package git.niklogik.db.entities.fire;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

@Entity(name = "FireInfo")
@Table(name = "fire_info")
public class FireInfoDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "direction")
    private double direction;
    @Column(name = "area")
    private double area;
    @Column(name = "fire_speed")
    private int fireSpeed;
    @Column(name = "fire_class")
    private int fireClass;
    @Column(name = "center_ignition")
    private Geometry center;

    public FireInfoDAO() {}
    public FireInfoDAO(double direction, int fireSpeed, int fireClass, Geometry center){
        this.direction = direction;
        this.fireSpeed = fireSpeed;
        this.fireClass = fireClass;
        this.center = center;
    }

    public long getId() {
        return id;
    }

    public double getDirection() {
        return direction;
    }

    public double getArea() {
        return area;
    }

    public int getFireSpeed() {
        return fireSpeed;
    }

    public int getFireClass() {
        return fireClass;
    }

    public Geometry getCenter() {
        return center;
    }
}
