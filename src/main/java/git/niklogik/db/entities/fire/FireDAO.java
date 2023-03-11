package git.niklogik.db.entities.fire;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "Fire")
@Table(name = "fires")
public class FireDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "fireId", fetch = FetchType.LAZY)
    Set<FireIterationDAO> iterations = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fire_info", referencedColumnName = "id")
    private FireInfoDAO fireInfo;

    @OneToOne
    @JoinColumn(name = "forest_type_id", referencedColumnName = "type_id")
    private ForestFuelTypeDao forestFuelTypeDao;

    public FireDAO() {
    }

    public FireDAO(String name, Date date, ForestFuelTypeDao forestFuelTypeDao, FireInfoDAO fireInfo) {
        this.name = name;
        this.date = date;
        this.forestFuelTypeDao = forestFuelTypeDao;
        this.fireInfo = fireInfo;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Set<FireIterationDAO> getIterations() {
        return iterations;
    }

    public FireInfoDAO getFireInfo() {
        return fireInfo;
    }

    public ForestFuelTypeDao getForestFuelType() {
        return forestFuelTypeDao;
    }
}
