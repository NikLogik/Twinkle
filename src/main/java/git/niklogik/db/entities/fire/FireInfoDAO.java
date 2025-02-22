package git.niklogik.db.entities.fire;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.locationtech.jts.geom.Geometry;

@Getter
@Entity(name = "FireInfo")
@Table(name = "fire_info")
public class FireInfoDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "direction")
    private Double direction;
    @Column(name = "area")
    private Double area;
    @Column(name = "fire_speed")
    private Double fireSpeed;
    @Column(name = "fire_class")
    private Integer fireClass;
    @Column(name = "center_ignition")
    private Geometry center;

    public FireInfoDAO() {}

    public FireInfoDAO(Double direction, Double fireSpeed, Integer fireClass, Geometry center){
        this.direction = direction;
        this.fireSpeed = fireSpeed;
        this.fireClass = fireClass;
        this.center = center;
    }
}
