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

}
