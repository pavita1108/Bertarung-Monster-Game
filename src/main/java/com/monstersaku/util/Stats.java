package com.monstersaku.util;

public class Stats {
    private double healthPoint;
    private double attack;
    private double defense;
    private double spesialAttack;
    private double spesialDefense;
    private double speed;
    

    // constructor
    public Stats (double healthPoint, double attack, double defense, double spesialAttack, double spesialDefense, double speed) {
        this.healthPoint = healthPoint;
        this.attack = attack;
        this.defense = defense;
        this.spesialAttack = spesialAttack;
        this.spesialDefense = spesialDefense;
        this.speed = speed;
    }

    // setter
    public void setHealtPoint (double healthPoint){
        this.healthPoint = healthPoint;
    }
    public void setAttack (double attack){
        this.attack = attack;
    }
    public void setDefense (double defense){
        this.defense = defense;
    }
    public void setSpesialAttack (double spesialAttack){
        this.spesialAttack = spesialAttack;
    }
    public void setSpesialDefense (double spesialDefense){
        this.spesialDefense = spesialDefense;
    }
    public void setSpeed (double speed){
        this.speed = speed;
    }

    // getter
    public double getHealthPoint(){
        return this.healthPoint;
    }
    public double getAttack(){
        return this.attack;
    }
    public double getDefense(){
        return this.defense;
    }
    public double getSpesialAttack(){
        return this.spesialAttack;
    }
    public double getSpesialDefense(){
        return this.spesialDefense;
    }
    public double getSpeed(){
        return this.speed;
    }
}
