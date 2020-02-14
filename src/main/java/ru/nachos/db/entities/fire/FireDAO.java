package ru.nachos.db.entities.fire;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
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
}
