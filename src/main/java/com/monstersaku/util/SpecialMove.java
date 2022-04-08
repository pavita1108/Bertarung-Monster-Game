package com.monstersaku.util;
import java.lang.Math;
public class SpecialMove extends Move{
    private int basepower;
    public SpecialMove (int id,String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, int basepower){
        super(id,name, elementType, accuracy, priority, ammunition,target);
        this.basepower = basepower;
    }

    public int getBasepower() {
        return basepower;
    }

    public void applyEffect (Monster attacker, Monster target,Effectivity effect){
        //effectivity 
        double randomAccuracy =  1 + (int)(Math.random() * ((100 - 1) + 1));
        double effectivity = 1;
        double burn = 1;
        if (randomAccuracy > super.getAccuracy()){
            System.out.println("Ga kena bos...");
        }
        else{
            for (int i = 0 ; i < target.getElementTypes().size() ; i++){
                ElementEffectivityKey a = new ElementEffectivityKey(super.getElementType(), target.getElementTypes().get(i));
                effect.getEffectivity(a);            
            }
            if (attacker.getIsBurn()){
                burn = 0.5;
            }
    
            //damage
            double damage= (double)Math.floor(basepower * (attacker.getStats().getSpesialAttack()/target.getStats().getSpesialDefense() + 2 ) * Math.random() * effectivity *burn);
            target.getStats().setHealtPoint(target.getStats().getHealthPoint() - damage);
            super.setAmmunition(super.getAmmunition() - 1);
            }
        }
        
}
