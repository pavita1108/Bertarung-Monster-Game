package com.monstersaku.util;
import java.util.ArrayList;
import java.util.List;
public class Player {
    private String nama;
    private List<Monster> listMonster = new ArrayList<>();
    private int jumlahMonster;

    public Player(String nama, List<Monster> listMonster) {
        this.nama = nama;
        this.listMonster = listMonster;
        this.jumlahMonster = 6;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<Monster> getListMonster() {
        return this.listMonster;
    }

    public void setListMonster(List<Monster> listMonster) {
        this.listMonster = listMonster;
    }

    public int getJumlahMonster() {
        return this.jumlahMonster;
    }

    public void setJumlahMonster(int jumlahMonster) {
        this.jumlahMonster = jumlahMonster;
    }
    public boolean isLose(){
        return (listMonster.get(0).getStats().getHealthPoint()<=0 && 
        listMonster.get(1).getStats().getHealthPoint()<=0 && 
        listMonster.get(2).getStats().getHealthPoint()<=0 && 
        listMonster.get(3).getStats().getHealthPoint()<=0 && 
        listMonster.get(4).getStats().getHealthPoint()<=0 && 
        listMonster.get(5).getStats().getHealthPoint()<=0);
    }

    public void printMonsters(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.printf("%s's Monsters%n", getNama());
        int i = 0;
        for(Monster monster : listMonster){
            i++;
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.printf("Id monster : %s%n", i);
            System.out.printf("Nama monster : %s%n", monster.getName());
            List<ElementType> elementTypes = monster.getElementTypes();
            for (ElementType elementType : elementTypes){
                if(elementType.equals(ElementType.NORMAL)){
                    System.out.printf("Element Type : %s%n", ElementType.NORMAL);
                }
                else if(elementType.equals(ElementType.FIRE)){
                    System.out.printf("Element Type : %s%n", ElementType.FIRE);
                }
                else if(elementType.equals(ElementType.WATER)){
                    System.out.printf("Element Type : %s%n", ElementType.WATER);
                }   
                else if(elementType.equals(ElementType.GRASS)){
                    System.out.printf("Element Type : %s%n", ElementType.GRASS);
                } 
            }
            monster.printStats();
        }
    }

}
