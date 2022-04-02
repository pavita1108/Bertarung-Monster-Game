package com.monstersaku.util;

<<<<<<< HEAD
public class Move {
=======
public class Move implements Cloneable{
    private int id;
>>>>>>> pav
    private String name;
    private ElementType elementType;
    private int accuracy;
    private int priority;
    private int ammunition;
<<<<<<< HEAD
    private int basePower;


    private int kind;
    /*
        kind -> jenis move
        1 = normal move
        2 = special move
        3 = default move
        4 = status move    
    */

    public Move (String name, ElementType elementType, int accuracy, int priority, int ammunition, int basePower, int kind) {
        this.name = name;
        this.elementType = elementType;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.basePower = basePower;
        this.kind = kind;
    }

    public void moveEffect (Monster attacker, Monster target) {
        // normal move
        if (this.kind == 1) {
            if (this.ammunition != 0) {
                double finaldamage= (double)Math.floor((this.basePower * (attacker.getStats().getAttack()/target.getStats().getDefense() + 2 ) * Math.random() ));
                // ini belum effectivity + status kondisi

                target.getStats().setHealtPoint(target.getStats().getHealthPoint() - finaldamage);

                this.ammunition -= 1;
            }
            else {
                System.out.println("Amunisi move ini habis");
            }
        }

        // special move
        else if (this.kind == 2) {
            if (this.ammunition != 0) {
                double finaldamage= (double)Math.floor((this.basePower * (attacker.getStats().getSpesialAttack()/target.getStats().getSpesialDefense() + 2 ) * Math.random() ));
                // belum pake effectivity juga

                target.getStats().setHealtPoint(target.getStats().getHealthPoint() - finaldamage);

                this.ammunition -= 1;
            }
            else {
                System.out.println("Amunisi move ini habis");
            }
        }

        // default move
        else if (this.kind == 3) {
            // ga perlu ngecek amunisi karena unlimited
            double finaldamage= (double)Math.floor((50 * (attacker.getStats().getAttack()/target.getStats().getDefense() + 2 ) * Math.random() ));
            target.getStats().setHealtPoint(target.getStats().getHealthPoint() - finaldamage);
        }
        
        // bonus move (status move)
        else if (this.kind == 4) {
            
        }
    }

}
=======

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



}
>>>>>>> pav
