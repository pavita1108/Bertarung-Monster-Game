package com.monstersaku.util;
import java.util.Random;


public class StatusMove extends Move{
    private Stats effect;
    private String status;
    private double hpeffect;
    private StatusBuff statsBuff;

    public StatusMove (int id,String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, String status,Stats effect){
        super(id,name, elementType, accuracy, priority, ammunition, target);
        this.effect = effect;
        this.status = status;
        hpeffect = effect.getHealthPoint();
        this.statsBuff = null;
        if (status.equals("BUFF") || status.equals("DEBUFF")){
            statsBuff = new StatusBuff();
        }
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

    public void applyEffect (Monster attacker, Monster target,Effectivity effectivity){
        double randomAccuracy =  1 + (int)(Math.random() * ((100 - 1) + 1));
        if (randomAccuracy > super.getAccuracy()){
            System.out.println("Ga kena bos...");
        }
        else{
            if (super.getTarget().equals("OWN")){
                if (this.statsBuff != null){
                    statsBuffCalculation(attacker);
                    System.out.println("Buff berhasil");
                }
                else{
                    Double temp = attacker.getStats().getHealthPoint() + ((this.hpeffect * attacker.getMaxHP())/100);
                    attacker.getStats().setHealtPoint(temp);
                    System.out.println("Heal berhasil menambah HP");
                }
                
            }
            else if (super.getTarget().equals("ENEMY")){
                if (this.statsBuff != null){
                    statsDebuffCalculation(target);
                }
                Double temp = target.getStats().getHealthPoint() - this.hpeffect;
                target.getStats().setHealtPoint(temp);
                if (!target.getIsBurn() && !target.getIsPoison() && !target.getIsSleep() && !target.getIsParalyze()){
                    if (this.status.equals("BURN")){   
                        target.setIsBurn(true); 
                        target.getStats().setHealtPoint(target.getStats().getHealthPoint()-(target.getMaxHP()/8));         
                        System.out.println("Terkena Burn");
                    }
                    else if (this.status.equals("POISON")){
                        target.setIsPoison(true);
                        target.getStats().setHealtPoint(target.getStats().getHealthPoint() - (target.getMaxHP()/16));
                        System.out.println("Terkena POISON");  
                    }
                    else if (this.status.equals("SLEEP")){
                        target.setIsSleep(true);
                        int min = 1;
                        int max = 7;
                        int random_round = (int)Math.floor(Math.random()*(max-min+1)+min);
                        target.setSleepCounter(random_round+1);
                        System.out.println("Terkena Sleep sebanyak" + (random_round+1));
                    }
                    else if (this.status.equals("PARALYZE")){    
                        target.getStats().setSpeed(target.getStats().getSpeed()-(target.getStats().getSpeed()/2));
                        System.out.printf("%s terkena Paralyze, Speed berkurang 1/2.%n", target.getName());

                        target.setIsParalyze(true);
                        System.out.printf("Paralyze efektif!");

                    }
                }
                else{
                    System.out.printf("%s sudah memiliki status condition lain!%n", target.getName());
                }
            } 
        }
        super.setAmmunition(super.getAmmunition() - 1);

    }

    public void statsBuffCalculation(Monster monster){
        Random random = new Random();
        int bound = 4;
        if (effect.getAttack() != 0){
            monster.getStats().setAttack(Math.floor(StatusBuff.getFactor(random.nextInt(bound)) * monster.getStats().getAttack()));
            System.out.printf("%s mendapatkan buff attack!!!%n", monster.getName());
        } 
        if (effect.getDefense()!= 0){
            monster.getStats().setDefense(Math.floor(StatusBuff.getFactor(random.nextInt(bound)) * monster.getStats().getDefense()));
            System.out.printf("%s mendapatkan buff defense!!!%n", monster.getName());
        }  
        if (effect.getSpesialAttack() != 0){
            monster.getStats().setSpesialAttack(Math.floor(StatusBuff.getFactor(random.nextInt(bound)) * monster.getStats().getSpesialAttack()));
            System.out.printf("%s mendapatkan buff special attack!!!%n", monster.getName());
        } 
        if (effect.getSpesialDefense() != 0){
            monster.getStats().setSpesialDefense(Math.floor(StatusBuff.getFactor(random.nextInt(bound)) * monster.getStats().getSpesialDefense()));
            System.out.printf("%s mendapatkan buff special defense!!!%n", monster.getName());
        }
        if (effect.getSpeed() != 0){
            monster.getStats().setSpeed(Math.floor(StatusBuff.getFactor(random.nextInt(bound)) * monster.getStats().getSpeed()));
            System.out.printf("%s mendapatkan buff speed!!!%n", monster.getName());
        }
    }

    public void statsDebuffCalculation(Monster monster){
        Random random = new Random();
        int bound = 4;
        if (effect.getAttack() != 0){
            monster.getStats().setAttack(Math.floor(StatusBuff.getFactor(random.nextInt(bound)-bound) * monster.getStats().getAttack()));
            System.out.printf("%s mendapatkan debuff attack!!!%n", monster.getName());
        } 
        if (effect.getDefense()!= 0){
            monster.getStats().setDefense(Math.floor(StatusBuff.getFactor(random.nextInt(bound)-bound) * monster.getStats().getDefense()));
            System.out.printf("%s mendapatkan debuff defense!!!%n", monster.getName());
        }  
        if (effect.getSpesialAttack() != 0){
            monster.getStats().setSpesialAttack(Math.floor(StatusBuff.getFactor(random.nextInt(bound)-bound) * monster.getStats().getSpesialAttack()));
            System.out.printf("%s mendapatkan debuff special attack!!!%n", monster.getName());
        } 
        if (effect.getSpesialDefense() != 0){
            monster.getStats().setSpesialDefense(Math.floor(StatusBuff.getFactor(random.nextInt(bound)-bound) * monster.getStats().getSpesialDefense()));
            System.out.printf("%s mendapatkan debuff special defense!!!%n", monster.getName());
        }
        if (effect.getSpeed() != 0){
            monster.getStats().setSpeed(Math.floor(StatusBuff.getFactor(random.nextInt(bound)-bound) * monster.getStats().getSpeed()));
            System.out.printf("%s mendapatkan buff speed!!!%n", monster.getName());
        }
    }
}
