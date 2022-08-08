package ru.nachos.db.entities.fire;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fires")
public class FireDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "fireId")
    Set<FireIterationDAO> iterations = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fire_info", referencedColumnName = "id")
    private FireInfoDAO fireInfo;

    @OneToOne
    @JoinColumn(name = "forest_type_id", referencedColumnName = "type_id")
    private ForestFuelType forestFuelType;

    public FireDAO() {}

    public FireDAO(String name, Date date, ForestFuelType forestFuelType, FireInfoDAO fireInfo){
        this.name = name;
        this.date = date;
        this.forestFuelType = forestFuelType;
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

    public ForestFuelType getForestFuelType() {
        return forestFuelType;
    }
}
