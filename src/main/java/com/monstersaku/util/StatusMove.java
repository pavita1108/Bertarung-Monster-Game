package com.monstersaku.util;

public class StatusMove extends Move{
    private Stats effect;
    private String status;

    public StatusMove (int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, String status,Stats effect){
        super(id,name, elementType, accuracy, priority, ammunition);
        this.effect = effect;
        this.status = status;
    }

    public Stats getEffect() {
        return this.effect;
    }

    public void setEffect(Stats effect) {
        this.effect = effect;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
