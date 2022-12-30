package git.niklogik.db.entities.fire;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "forest_type", schema = "public")
public class ForestFuelType implements git.niklogik.core.network.lib.ForestFuelType, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "type_id")
    private int typeId;
    @Column(name = "type_name")
    private String name;
    @Column(name = "middle_reserve")
    private double middleReserve;       //  средний запас ЛГМ
    @Column(name = "specific_area")
    private int specificArea;           //  удельная поверхность ЛГМ
    @Column(name = "heat")
    private int heat;                //  теплота сгорания
    @Column(name = "density_fuel")
    private int densityForestFuel;      //  плотность ЛГМ
    @Column(name = "depth_layer")
    private double depth;               //  толщина слоя ЛГМ
    @Column(name = "moisture")
    private double moisture;            //  влагосодержание
    @Column(name = "max_moisture")
    private double maxMoisture;         //  предельное влагосодержание
    @Column(name = "mineral_matter")
    private final double mineralMatter = 0.02;       //  содержание минеральных веществ const=0.02
    @Column(name = "mineral_consistency")
    private final double mineralConsistency = 0.42; //  коэффициент минерального состава в ЛГМ const=0.42
    @Column(name = "tree_height")
    private int treeHeight;             //  средняя высота древостоя
    @Column(name = "canopy_wood")
    private double canopyOfWood;       //  высота полога древостоя
    @Column(name = "capacity_density")
    private double capacityDensityWood;

    @Override
    public long getId() {
        return id;
    }

    public int getTypeId() {
        return typeId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getMiddleReserve() {
        return middleReserve;
    }

    @Override
    public int getSpecificArea() {
        return specificArea;
    }

    @Override
    public int getHeat() {
        return heat;
    }

    @Override
    public int getDensityForestFuel() {
        return densityForestFuel;
    }

    @Override
    public double getDepth() {
        return depth;
    }

    @Override
    public double getMoisture() {
        return moisture;
    }

    @Override
    public double getMaxMoisture() {
        return maxMoisture;
    }

    @Override
    public double getMineralMatter() {
        return mineralMatter;
    }

    @Override
    public double getMineralConsistency() {
        return mineralConsistency;
    }

    @Override
    public int getTreeHeight() {
        return treeHeight;
    }

    @Override
    public double getCanopyOfWood() {
        return canopyOfWood;
    }

    @Override
    public double getCapacityDensityWood() {
        return capacityDensityWood;
    }
}
