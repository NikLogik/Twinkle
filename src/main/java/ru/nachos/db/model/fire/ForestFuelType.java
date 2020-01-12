package ru.nachos.db.model.fire;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "forest_type", schema = "public")
@Data
public class ForestFuelType implements ru.nachos.core.network.lib.ForestFuelType {
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
    private double  canopyOfWood;       //  высота полога древостоя
    @Column(name = "capacity_density")
    private double capacityDensityWood;
}
