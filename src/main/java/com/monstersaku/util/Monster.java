package com.monstersaku.util;
import java.util.ArrayList;
import java.util.List;

public class Monster {
    private String name;
    private List<ElementType> elmtTypes = new ArrayList<>();
    private Stats baseStats;
    private List<Move> moves = new ArrayList<>();
    

    public Monster(String name, List<ElementType> elementTypes, Stats baseStats, List<Move> moves){
        this.name = name;
        this.elmtTypes = elementTypes;
        this.baseStats = baseStats;
        this.moves = moves;
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
}