package com.monstersaku.util;
import java.util.HashMap;

public class Effectivity {


    public static Double getEffectivity(ElementEffectivityKey elmtKey, HashMap<ElementEffectivityKey,Double> map){
        return map.get(elmtKey);
    }
}

