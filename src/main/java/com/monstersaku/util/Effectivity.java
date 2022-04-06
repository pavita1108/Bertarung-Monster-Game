package com.monstersaku.util;
import java.util.HashMap;

public class Effectivity {

    private HashMap<ElementEffectivityKey, Double> effectivityList;

    public Effectivity() {
        this.effectivityList = new HashMap<ElementEffectivityKey, Double>();
    }

    public void add(ElementEffectivityKey a, double effectivity) {
        this.effectivityList.put(a, effectivity);
    }

    public double getEffectivity (ElementEffectivityKey a) {
        double effectivity = this.effectivityList.get(a);
        return effectivity;
    }
}

