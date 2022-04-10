package com.monstersaku.util;
import java.util.ArrayList;
import java.util.List;

public class Monster {
    private int id;
    private String name;
    private List<ElementType> elmtTypes = new ArrayList<>();
    private Stats baseStats;
    private List<Move> moves = new ArrayList<>();

    // dibawah ini atribut status condition
    private boolean isBurn;
    private boolean isPoison;
    private boolean isSleep;
    private boolean isParalyze;
    private int sleepCounter;
    private Double maxHp;
    private boolean isDead;

    public Monster(int id ,String name, List<ElementType> elementTypes, Stats baseStats, List<Move> moves){
        
        this.id = id;
        this.name = name;
        this.elmtTypes = elementTypes;
        this.baseStats = baseStats;
        this.moves = moves;

        this.isBurn = false;
        this.isPoison = false;
        this.isSleep = false;
        this.isParalyze = false;
        this.sleepCounter = 0;
        this.isDead = false;
        this.maxHp = baseStats.getHealthPoint();
    }

    public String getName(){
        return this.name;
    }
    public int getId(){
        return this.id;
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

    public int getSleepCounter() {
        return this.sleepCounter;
    }

    public Double getMaxHP() {
        return this.maxHp;
    }
    public boolean getIsDead(){
        if (baseStats.getHealthPoint() <= 0){
            isDead = true;  
        }
        return isDead;
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

    public void setSleepCounter(int num) {
        this.sleepCounter = num;
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

    public void printStats (){
        if (isDead){
            System.out.println(name + " sudah mati");
        }
        else{
            System.out.println(name + "'s " + "Current Stats");
            System.out.println("Health Point: " + baseStats.getHealthPoint());
            System.out.println("Attack: " + baseStats.getAttack());
            System.out.println("Defense: " + baseStats.getDefense());
            System.out.println("Special Attack: " + baseStats.getSpesialAttack());
            System.out.println("Special Defense: " + baseStats.getSpesialDefense());
            System.out.println("Speed: " + baseStats.getSpeed());
            System.out.println("");
        }
        
    }
}