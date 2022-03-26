package com.monstersaku.util;
import java.util.ArrayList;
import java.util.List;

public class Monster {
    private String name;
    private List<ElementType> elmtTypes = new ArrayList<>();
    private Stats baseStats;
    private List<Move> moves = new ArrayList<>();

    // dibawah ini atribut status condition
    private boolean isBurn;
    private boolean isPoison;
    private boolean isSleep;
    private boolean isParalyze;
    

    public Monster(String name, List<ElementType> elementTypes, Stats baseStats, List<Move> moves, 
    boolean isBurn, boolean isPoison, boolean isSleep, boolean isParalyze){
        
        this.name = name;
        this.elmtTypes = elementTypes;
        this.baseStats = baseStats;
        this.moves = moves;

        this.isBurn = isBurn;
        this.isPoison = isPoison;
        this.isSleep = isSleep;
        this.isParalyze = isParalyze;
    }

    public String getNameOfMonster(){
        return this.name;
    }

    public List<ElementType> getElementTypes(){
        return this.elmtTypes;
    }

    public List<Move> getMoves(){
        return this.moves;
    }

    public Stats getStats(){
        return this.baseStats;
    }

    public void setElmt(ElementType elmtType){
        elmtTypes.add(elmtType);
    }

    public void setMove(Move mv){
        moves.add(mv);
    }

    public void setStats(Stats s){
        baseStats = s;
    }

    // method status condition
    
        // getter status condition
    public boolean getIsBurn() {
        return this.isBurn;
    }

    public boolean getIsPoison() {
        return this.isPoison;
    }

    public boolean getIsSleep() {
        return this.isSleep;
    }

    public boolean getIsParalyze() {
        return this.isParalyze;
    }

        // setter status condition 
    public void setIsBurn(boolean status) {
        this.isBurn = status;
    }

    public void setIsPoison(boolean status) {
        this.isPoison = status;
    }

    public void setIsSleep(boolean status) {
        this.isSleep = status;
    }

    public void setIsParalyze(boolean status) {
        this.isParalyze = status;
    }

        // method untuk cek apakah monster ada condition yang aktif

    public boolean isAnyConditionActive() {
        if (getIsBurn() == true || getIsPoison() == true || getIsSleep() == true || getIsParalyze() == true) {
            return true;
        }
        else {
            return false;
        }
    }
}