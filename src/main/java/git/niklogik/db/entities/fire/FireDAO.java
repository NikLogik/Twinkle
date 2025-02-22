package git.niklogik.db.entities.fire;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter

@Entity
@Table(name = "fires")
public class FireDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public ForestFuelTypeDao getForestFuelType() {
        return forestFuelTypeDao;
    }
}
