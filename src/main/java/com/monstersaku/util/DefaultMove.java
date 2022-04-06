package com.monstersaku.util;

import java.util.*;

public class DefaultMove extends Move{
    private int basepower;
    public DefaultMove (){
        super(1,"Deafult", ElementType.NORMAL, 100, 0, 999 , "ENEMY");
        this.basepower = 50;
    }

    public void applyEffect (Monster attacker, Monster target,Effectivity effect){
        
    }

}
