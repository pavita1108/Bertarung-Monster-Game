package com.monstersaku.util;

public class SpecialMove extends Move{
    private int basepower;
    public SpecialMove (int id , String name, ElementType elementType, int accuracy, int priority, int ammunition, int basepower){
        super(id,name, elementType, accuracy, priority, ammunition);
        this.basepower = basepower;
    }

    public int getBasepower() {
        return basepower;
    }

}
