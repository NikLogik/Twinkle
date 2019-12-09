package ru.nachos.core.utils;

import java.util.HashMap;
import java.util.Map;

public enum  PolygonType {
    //Key:natural
    VOLCANO("volcano"),
    STRAIT("strait"),
    TREE_ROW("tree_row"),
    MUD("mud"),
    SHINGLE("shingle"),
    CANYON("canyon"),
    VALLEY("valley"),
    GRASSLAND("grassland"),
    CLIFF("cliff"),
    SAND("sand"),
    BURNED_SCRUB("burned:scrub"),
    SCREE("scree"),
    SCREE_2("scree СНиП 23-01-99 «Строительная климатология»"),
    WATER("water"),
    GRASS("grass"),
    EARTH_BANK("earth_bank"),
    WOOD("wood"),
    SPRING("spring"),
    SCRUB("scrub"),
    BARE_ROCK("bare_rock"),
    SINKHOLE("sinkhole"),
    RAVINE("ravine"),
    STONES("stones"),
    PART("part"),
    PARK("park"),
    BEACH("beach"),
    FELL("fell"),
    WETLAND("wetland"),
    TREE("tree"),
    HEATH("heath"),
    PENINSULA("peninsula"),
    CAPE("cape"),
    CAVE("cave"),
    BAY("bay"),
    STONE("stone"),
    ROCK("rock"),
    MOOR("moor"),
    DEFAULT("default"),

    //Key:landuse
    MEADOW("meadow"),
    ORCHARD("orchard"),
    VINEYARD("vineyard"),
    FOREST("forest"),
    FARMYARD("farmyard"),
    FARMLAND("farmland"),
    ALLOTMENTS("allotments"),
    BASIN("basin"),
    BROWNFIELD("brownfield"),
    CEMETERY("cemetery"),
    DEPOT("depot"),
    GARAGES("garages"),
    GRASS_LANDUSE("grass"),
    GREENFIELD("greenfield"),
    GREENFIELD_HORTICULTURE("greenfield_horticulture"),
    LANDFILL("landfill"),
    MILITARY("military"),
    PLANT_NURSERY("plant_nursery"),
    PORT("port"),
    QUARRY("quarry"),
    RAILWAY("railway"),
    RECREATION_GROUND("recreation_ground"),
    RELIGIOUS("religious"),
    RESERVOIR("reservoir"),
    SALT_POND("salt_pond"),
    VILLAGE_GREEN("village_green");

    private String param;

    private static final Map<String, PolygonType> BY_LABEL = new HashMap<>();

    static {
        for (PolygonType type: values()) {
            BY_LABEL.put(type.param, type);
        }
    }

    PolygonType(String param){
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public static PolygonType valueOfType(String label) {
        if (label == null){
            return DEFAULT;
        } else {
            return BY_LABEL.get(label);
        }
    }

    public static PolygonType valueOfLanduse(String label){
        PolygonType type = BY_LABEL.get(label);
        if (type==null){
            return DEFAULT;
        }
        switch (type){
            case MEADOW:
            case ORCHARD:
            case VINEYARD:
            case FARMLAND:
            case GRASS_LANDUSE:
            case GREENFIELD:
            case GREENFIELD_HORTICULTURE:
            case LANDFILL:
            case PLANT_NURSERY:
            case RECREATION_GROUND:
            case VILLAGE_GREEN:
                type = GRASS;
                break;
            case FOREST:
            case FARMYARD:
            case ALLOTMENTS:
                type = WOOD;
                break;
            case BASIN:
            case RESERVOIR:
                type = WATER;
                break;
            case BROWNFIELD:
            case CEMETERY:
            case DEPOT:
            case GARAGES:
            case MILITARY:
            case PORT:
            case QUARRY:
            case RELIGIOUS:
            case SALT_POND:
                type = DEFAULT;
                break;
        }
        return type;
    }

}
