package com.monstersaku.util;
import java.io.ObjectInputFilter.Status;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class StatusBuff {
    private Map<Integer, Double> statusFactor = Stream.of(new double[][] { { -4, -3, -2, -1, 0, 1, 2, 3, 4 }, {2/6, 2/5, 2/4, 2/3, 1, 3/2, 4/2, 5/2, 6/2 }, }).collect(Collectors.toMap(data -> (int) data[0], data -> data[1]));
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public StatusBuff(int a, int d, int sa, int sd, int s){
        this.attack = a;
        this.defense = d;
        this.specialAttack = sa;
        this.specialDefense = sd;
        this.speed = s;
    }


    public double getFactor(int rnd){
        return statusFactor.get(rnd);
    }

    public void statsCalculation(Stats stats){
        stats.setAttack(stats.getAttack()*getFactor(attack));
        stats.setDefense(stats.getDefense()*getFactor(defense));
        stats.setSpesialAttack(stats.getSpesialAttack()*getFactor(specialAttack));
        stats.setSpesialDefense(stats.getSpesialDefense()*getFactor(specialDefense));
        stats.setSpeed(stats.getSpeed()*getFactor(speed));
    }

}


