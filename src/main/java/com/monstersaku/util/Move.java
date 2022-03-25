package com.monstersaku.util;

public class Move {
    private int id;
    private String name;
    private ElementType elementType;
    private int accuracy;
    private int priority;
    private int ammunition;
    private int basepower;

    public Move (){

    }

    public void DefaultMove (){
        Move defaultmove = new Move();
        defaultmove.id=99999;//idmove
        defaultmove.ammunition=9999999;
        defaultmove.basepower=50;
        defaultmove.priority=0;

    }

    public void NormalMove (Monster player , Monster target){
        float finaldamage= (float)Math.floor((this.basepower * (player.getStats().getAttack()/target.getStats().getDefense() + 2 ) * Math.random() ));
        // ini belum effectivity + status kondisi
    }
}
