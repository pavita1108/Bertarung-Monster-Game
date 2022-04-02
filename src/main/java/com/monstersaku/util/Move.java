package com.monstersaku.util;

public class Move implements Cloneable{
    private int id;
    private String name;
    private ElementType elementType;
    private int accuracy;
    private int priority;
    private int ammunition;

    public Move (int id,String name, ElementType elementType, int accuracy, int priority, int ammunition) {
        this.id = id;
        this.name = name;
        this.elementType = elementType;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
    }

    public double damage (Monster attacker, Monster target,int basePower) {
        double damage;
        damage= (double)Math.floor((basePower * (attacker.getStats().getAttack()/target.getStats().getDefense() + 2 ) * Math.random() ));
        return damage;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    };



}