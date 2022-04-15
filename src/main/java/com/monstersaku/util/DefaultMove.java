package com.monstersaku.util;


public class DefaultMove extends Move{
    private int basepower;
    public DefaultMove (){
        super(1,"Deafult", ElementType.NORMAL, 100, 0, 999 , "ENEMY");
        this.basepower = 50;
    }

    public void applyEffect (Monster attacker, Monster target,Effectivity effect){
        double effectivity = 1;
        double burn = 1;
        for (int i = 0 ; i < target.getElementTypes().size() ; i++){
            ElementEffectivityKey a = new ElementEffectivityKey(super.getElementType(), target.getElementTypes().get(i));
            effectivity = effectivity*effect.getEffectivity(a);
        }
        if (attacker.getIsBurn()){
            burn = 0.5;
        }
        //damage
        double damage= (double)Math.floor(basepower * (attacker.getStats().getAttack()/target.getStats().getDefense() + 2 ) * Math.random() * effectivity *burn);
        target.getStats().setHealtPoint(target.getStats().getHealthPoint() - damage);
        attacker.getStats().setHealtPoint(attacker.getStats().getHealthPoint() -  (attacker.getMaxHP()/4));
        super.setAmmunition(super.getAmmunition() - 1);
    }

}
