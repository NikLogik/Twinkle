package ru.nachos.core.network;

import ru.nachos.core.Id;

public class ForestFuelTypeImpl implements ru.nachos.core.network.lib.ForestFuelType {

    private Id<ForestFuelTypeImpl> id;
    private String name;
    private double middleReserve;       //  средний запас ЛГМ
    private int specificArea;           //  удельная поверхность ЛГМ
    private int heat;                //  теплота сгорания
    private int densityForestFuel;      //  плотность ЛГМ
    private double depth;               //  толщина слоя ЛГМ
    private double moisture;            //  влагосодержание
    private double maxMoisture;         //  предельное влагосодержание
    private final double mineralMatter = 0.02;       //  содержание минеральных веществ const=0.02
    private final double mineralConsistency = 0.42; //  коэффициент минерального состава в ЛГМ const=0.42
    private int treeHeight;             //  средняя высота древостоя
    private double  canopyOfWood;       //  высота полога древостоя
    private double capacityDensityWood; //  объемная плотность полога леса

    public ForestFuelTypeImpl(int id, String name, double middleReserve, int specificArea, int heat,
                              int densityForestFuel, double depth, double moisture, double maxMoisture, int treeHeight,
                              double canopyOfWood, double capacityDensityWood) {
        this.id = Id.create(id, ForestFuelTypeImpl.class);
        this.name = name;
        this.middleReserve = middleReserve;
        this.specificArea = specificArea;
        this.heat = heat;
        this.densityForestFuel = densityForestFuel;
        this.depth = depth;
        this.moisture = moisture;
        this.maxMoisture = maxMoisture;
        this.treeHeight = treeHeight;
        this.canopyOfWood = canopyOfWood;
        this.capacityDensityWood = capacityDensityWood;
    }

    @Override
    public String getName() { return name; }
    @Override
    public Id<ForestFuelTypeImpl> getId() { return this.id; }
    @Override
    public double getMiddleReserve() { return this.middleReserve; }
    @Override
    public int getSpecificArea() { return this.specificArea; }
    @Override
    public int getHeat() { return this.heat; }
    @Override
    public int getDensityForestFuel() {return this.densityForestFuel;}
    @Override
    public double getDepth() {return this.depth;}
    @Override
    public double getMoisture() {return this.moisture;}
    @Override
    public double getMaxMoisture() {return this.maxMoisture;}
    @Override
    public double getMineralMatter() {return this.mineralMatter;}
    @Override
    public double getMineralConsistency() {return this.mineralConsistency;}
    @Override
    public int getTreeHeight() {return this.treeHeight;}
    @Override
    public double getCanopyOfWood() { return canopyOfWood; }
    @Override
    public double getCapacityDensityWood() {return this.capacityDensityWood;}
}
