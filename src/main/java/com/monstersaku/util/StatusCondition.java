package com.monstersaku.util;
import java.util.Random;
public class StatusCondition {
    public StatusCondition (){

    }

    public static void Burn (Monster mons){
        double damage = mons.getMaxHP()/8;
        mons.getStats().setHealtPoint(mons.getStats().getHealthPoint() - damage);
    }

    public static void Poison (Monster mons){
        double damage = mons.getMaxHP()/16;
        mons.getStats().setHealtPoint(mons.getStats().getHealthPoint() - damage);
    }

    public static void Paralyze (Monster mons){
        mons.getStats().setSpeed(mons.getStats().getSpeed()/2);
        
    }

    public static void Sleep (Monster mons){
        Random rand = new Random();
        int rand_int = rand.nextInt(7) + 1;
        mons.setSleepCounter(rand_int);
    }
}
