package com.monstersaku.util;

public class StatusMove extends Move{
    private Stats effect;
    private String status;
    private double hpeffect;

    public StatusMove (int id,String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, String status,Stats effect){
        super(id,name, elementType, accuracy, priority, ammunition, target);
        this.effect = effect;
        this.status = status;
        hpeffect = effect.getHealthPoint();
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
                Double temp = attacker.getStats().getHealthPoint() + ((this.hpeffect * attacker.getMaxHP())/100);
                attacker.getStats().setHealtPoint(temp);
                System.out.println("Heal berhasil menambah HP");
            }
            else if (super.getTarget().equals("ENEMY")){
                Double temp = target.getStats().getHealthPoint() - this.hpeffect;
                target.getStats().setHealtPoint(temp);
                if (!target.getIsBurn() && !target.getIsPoison() && !target.getIsSleep() && !target.getIsParalyze()){
                    if (this.status.equals("BURN")){   
                        target.setIsBurn(true); 
                        target.getStats().setHealtPoint(target.getStats().getHealthPoint()-(target.getMaxHP()/8));    
                        target.getStats().setAttack(target.getStats().getAttack()/2);
                        target.getStats().setSpesialAttack(target.getStats().getSpesialAttack()/2);             
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
}
