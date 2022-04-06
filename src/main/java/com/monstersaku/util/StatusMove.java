package com.monstersaku.util;

public class StatusMove extends Move{
    private Stats effect;
    private String status;

    public StatusMove (int id,String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, String status,Stats effect){
        super(id,name, elementType, accuracy, priority, ammunition, target);
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

    public void applyEffect (Monster attacker, Monster target,Effectivity effectivity){
        if (super.getTarget().equals("OWN")){
            Double healthPoint = attacker.getStats().getHealthPoint() + effect.getHealthPoint();
            Double attack = attacker.getStats().getAttack() + effect.getAttack();
            Double defense = attacker.getStats().getDefense() + effect.getDefense();
            Double spesialAttack = attacker.getStats().getSpesialAttack() + effect.getSpesialAttack();
            Double spesialDefense = attacker.getStats().getSpesialDefense() + effect.getSpesialDefense();
            Double speed = attacker.getStats().getSpeed() + effect.getSpeed();

            Stats a = new Stats(healthPoint, attack, defense, spesialAttack, spesialDefense, speed);
            attacker.setStats(a);
            
        }
        else{
            Double healthPoint = target.getStats().getHealthPoint() + effect.getHealthPoint();
            Double attack = target.getStats().getAttack() + effect.getAttack();
            Double defense = target.getStats().getDefense() + effect.getDefense();
            Double spesialAttack = target.getStats().getSpesialAttack() + effect.getSpesialAttack();
            Double spesialDefense = target.getStats().getSpesialDefense() + effect.getSpesialDefense();
            Double speed = target.getStats().getSpeed() + effect.getSpeed();

            Stats a = new Stats(healthPoint, attack, defense, spesialAttack, spesialDefense, speed);
            target.setStats(a);
        }
    }
}
