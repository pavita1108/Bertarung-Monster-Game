package com.monstersaku.util;

public class StatusBuff {
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public StatusBuff(){
        this.attack = 0;
        this.defense = 0;
        this.specialAttack = 0;
        this.specialDefense = 0;
        this.speed = 0;
    }

    public static double getFactor(int rnd) {
        switch (rnd) {
            case -4:
                return ((double) 2/6);
            case -3:
                return ((double) 2/5);
            case -2:
                return ((double) 2/4);
            case -1:
                return ((double) 2/3);
            case 0:
                return ((double) 1);
            case 1:
                return ((double) 3/2);
            case 2:
                return ((double) 4/2);
            case 3:
                return ((double) 5/2);
            case 4:
                return ((double) 6/2);
            default:
                return ((double) 1);
        }
    }

}


