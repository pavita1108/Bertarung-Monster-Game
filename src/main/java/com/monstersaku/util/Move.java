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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementType getElementType() {
        return this.elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAmmunition() {
        return this.ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }





}
