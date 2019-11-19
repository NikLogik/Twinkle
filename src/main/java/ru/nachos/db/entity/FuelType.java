package ru.nachos.db.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "forest_fuel_type")
public class FuelType {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Getter @Setter
    @Column(name = "fuel_id")
    private int fuelTypeId;
    @Getter @Setter
    @Column(name = "fuel_name")
    private String name;
    @Getter @Setter
    @Column(name = "mid_reserve")
    private double middleReserve;       //  средний запас ЛГМ
    @Getter @Setter
    @Column(name = "unit_area")
    private int unitArea;           //  удельная поверхность ЛГМ
    @Getter @Setter
    @Column(name = "heat")
    private int heat;                //  теплота сгорания
    @Getter @Setter
    @Column(name = "forest_density")
    private int densityForestFuel;      //  плотность ЛГМ
    @Getter @Setter
    @Column(name = "depth_layer")
    private double depth;               //  толщина слоя ЛГМ
    @Getter @Setter
    @Column(name = "moisture")
    private double moisture;            //  влагосодержание
    @Getter @Setter
    @Column(name = "max_moisture")
    private double maxMoisture;         //  предельное влагосодержание
    @Getter @Setter
    @Column(name = "mineral_content")
    private double mineralContent;       //  содержание минеральных веществ const=0.02
    @Getter @Setter
    @Column(name = "mineral_consist")
    private double mineralConsistency; //  коэффициент минерального состава в ЛГМ const=0.42
    @Getter @Setter
    @Column(name = "tree_height")
    private int treeHeight;             //  средняя высота древостоя
    @Getter @Setter
    @Column(name = "height_canopy_wood")
    private double  canopyOfWood;       //  высота полога древостоя
    @Getter @Setter
    @Column(name = "cap_density")
    private double capacityDensityWood; //  объемная плотность полога леса

    public FuelType(){}

    public FuelType(int fuelTypeId, String name, double middleReserve, int unitArea, int heat,
                    int densityForestFuel, double depth, double moisture, double maxMoisture, int treeHeight,
                    double canopyOfWood, double capacityDensityWood) {
        this.fuelTypeId = fuelTypeId;
        this.name = name;
        this.middleReserve = middleReserve;
        this.unitArea = unitArea;
        this.heat = heat;
        this.densityForestFuel = densityForestFuel;
        this.depth = depth;
        this.moisture = moisture;
        this.maxMoisture = maxMoisture;
        this.treeHeight = treeHeight;
        this.canopyOfWood = canopyOfWood;
        this.capacityDensityWood = capacityDensityWood;
        this.mineralContent = 0.02;
        this.mineralConsistency = 0.42;
    }
}
