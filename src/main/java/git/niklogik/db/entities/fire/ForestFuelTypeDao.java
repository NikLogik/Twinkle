package git.niklogik.db.entities.fire;

import git.niklogik.core.network.lib.ForestFuelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter

@Entity
@Table(name = "forest_type", schema = "public")
public class ForestFuelTypeDao implements ForestFuelType, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "type_id")
    private int typeId;
    @Column(name = "type_name")
    private String name;
    @Setter @Column(name = "middle_reserve")
    private double middleReserve;       //  средний запас ЛГМ
    @Setter @Column(name = "specific_area")
    private int specificArea;           //  удельная поверхность ЛГМ
    @Column(name = "heat")
    private int heat;                //  теплота сгорания
    @Column(name = "density_fuel")
    private int densityForestFuel;      //  плотность ЛГМ
    @Setter @Column(name = "depth_layer")
    private double depth;               //  толщина слоя ЛГМ
    @Column(name = "moisture")
    private double moisture;            //  влагосодержание
    @Column(name = "max_moisture")
    private double maxMoisture;         //  предельное влагосодержание
    @Column(name = "mineral_matter")
    private final double mineralMatter = 0.02;       //  содержание минеральных веществ const=0.02
    @Column(name = "mineral_consistency")
    private final double mineralConsistency = 0.42; //  коэффициент минерального состава в ЛГМ const=0.42
    @Setter @Column(name = "tree_height")
    private int treeHeight;             //  средняя высота древостоя
    @Column(name = "canopy_wood")
    private double canopyOfWood;       //  высота полога древостоя
    @Column(name = "capacity_density")
    private double capacityDensityWood;
}
