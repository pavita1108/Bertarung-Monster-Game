package com.monstersaku.util;
import java.util.*;
public class NormalMove extends Move{
    private int basepower;

    public NormalMove (int id,String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, int basepower){
        super(id,name, elementType, accuracy, priority, ammunition , target);
        this.basepower = basepower;
    }

    public int getBasepower() {
        return basepower;
    }

    public void applyEffect (Monster attacker, Monster target,HashMap<ElementEffectivityKey,Double> map){
        //effectivity 
        double effectivity = 1;
        double burn = 1;
        for (int i = 0 ; i < target.getElementTypes().size() ; i++){
            ElementEffectivityKey a = new ElementEffectivityKey(super.getElementType(), target.getElementTypes().get(i));
            effectivity = effectivity * Effectivity.getEffectivity(a, map);
        }
        if (attacker.getIsBurn()){
            burn = 0.5;
        }

        //damage
        double damage= (double)Math.floor(basepower * (attacker.getStats().getAttack()/target.getStats().getDefense() + 2 ) * Math.random() * effectivity *burn);
        target.getStats().setHealtPoint(target.getStats().getHealthPoint() - damage);
        super.setAmmunition(super.getAmmunition() - 1);

    }
}
