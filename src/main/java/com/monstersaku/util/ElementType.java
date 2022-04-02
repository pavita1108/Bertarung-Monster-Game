package com.monstersaku.util;
import java.util.HashMap;
import java.util.List;
import java.io.File;


public enum  ElementType {
    NORMAL,
    FIRE,
    WATER,
    GRASS;
    
    private HashMap<ElementEffectivityKey,Float> map = configEffectivity();
    // private static ElementType single_instance = null;
    
    
    private CSVReader monsterReader;

    /*Read CSV File*/
    /*FILE PATHNYA JANGAN LUPA GANTI !!!!*/ 
    private static final File path = new File("C:/Users/User/Documents/OOP/TUBES_OOP/monster-saku-project-templat/src/main/resources/com/monstersaku/configs/element-type-effectivity-chart.csv");


    private HashMap<ElementEffectivityKey, Float> configEffectivity() {
        try {
            map = new HashMap<>();
            monsterReader = new CSVReader(path, ",");
            List<String[]> elmtEff = monsterReader.read();
            for (String [] s : elmtEff){
                ElementEffectivityKey source_target = new ElementEffectivityKey(ElementType.valueOf(s[0]),ElementType.valueOf(s[1]));
                map.put(source_target, Float.parseFloat(s[2]));
            }
            return map;
        } catch (Exception e){
            return null;
        } 
    }
    public float getEffectivity(ElementEffectivityKey elmtKey){
        return map.getOrDefault(elmtKey, 1F);
    }

    // public static ElementType getInstance() throws IOException{
    //     if (single_instance == null)
    //         single_instance = new ElementType();
 
    //     return single_instance;
    // }
}
