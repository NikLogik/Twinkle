package git.niklogik.db.entities.fire;

import lombok.Getter;
import org.locationtech.jts.geom.Geometry;

import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "fire_iterations")
public class FireIterationDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "iter_number")
    private int iterNumber;
    @Column(name = "iter_amount")
    private int iterAmount;
    @Column(name = "polygon")
    private Geometry polygon;
    @ManyToOne
    @JoinColumn(name = "fire_id")
    private FireDAO fireId;

    public FireIterationDAO() {
    }

    public FireIterationDAO(int iterNumber, int iterAmount, Geometry polygon, FireDAO fireId) {
        this.iterNumber = iterNumber;
        this.iterAmount = iterAmount;
        this.polygon = polygon;
        this.fireId = fireId;
    }
}
