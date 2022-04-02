package com.monstersaku;

import com.monstersaku.util.CSVReader;
import com.monstersaku.util.Move;
import com.monstersaku.util.Monster;
import com.monstersaku.util.ElementType;
import com.monstersaku.util.Effectivity;
import com.monstersaku.util.ElementEffectivityKey;
import com.monstersaku.util.Stats;
import com.monstersaku.util.NormalMove;
import com.monstersaku.util.SpecialMove;
import com.monstersaku.util.StatusMove;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));

    public static void main(String[] args) {
        //atribut untuk nyimpen bacaan csv
        ArrayList<Move> listMoves = new ArrayList<>();
        ArrayList<Monster> listMonster = new ArrayList<>();
        //mulai baca csv pake try catch
        try{
            //baca movepool
            CSVReader reader = new CSVReader(new File(Main.class.getResource("configs/movepool.csv").toURI()), ";");
            reader.setSkipHeader(true);
            List<String[]> lines = reader.read();
            System.out.println("----START-----");
            for (String[] line: lines){
                int id = Integer.parseInt(line[0]);
                String moveType = line[1];
                String name = line[2];
                ElementType elType = ElementType.valueOf(line[3]);
                int accuracy = Integer.parseInt(line[4]);
                int priority = Integer.parseInt(line[5]);
                int ammunition = Integer.parseInt(line[6]);
                
                if (moveType.equals("STATUS")){
                    //baca + masukin data ke objec stats
                    String moveStatus = line[8];
                    String moveStats = line[9];
                    String[] arrofmovestats = moveStats.split(",",6);
                    Double hp = Double.parseDouble(arrofmovestats[0]);
                    Double atk = Double.parseDouble(arrofmovestats[1]);
                    Double def = Double.parseDouble(arrofmovestats[2]);
                    Double spcAtk = Double.parseDouble(arrofmovestats[3]);
                    Double spcDef = Double.parseDouble(arrofmovestats[4]);
                    Double speed = Double.parseDouble(arrofmovestats[5]);
                    Stats stat = new Stats(hp,atk,def,spcAtk,spcDef,speed);

                    StatusMove a = new StatusMove(id, name, elType, accuracy, priority, ammunition, moveStatus, stat);

                    listMoves.add(a);
                }
                else if (moveType.equals("NORMAL")){
                    int effect = Integer.parseInt(line[8]);

                    NormalMove b = new NormalMove(id, name, elType, accuracy, priority, ammunition, effect);

                    listMoves.add(b);
                }
                else if (moveType.equals("SPECIAL")){
                    int effect = Integer.parseInt(line[8]);

                    SpecialMove c = new SpecialMove(id, name, elType, accuracy, priority, ammunition, effect);

                    listMoves.add(c);
                }
            }
            
            //baca monster pool
            CSVReader reader1 = new CSVReader(new File(Main.class.getResource("configs/monsterpool.csv").toURI()), ";");
            reader1.setSkipHeader(true);
            List<String[]> lines1 = reader1.read();
            for (String[] line1 : lines1){
                //dimasukin ke var dulu
                int id = Integer.parseInt(line1[0]);
                String name = line1[1];
                String element = line1[2];
                String stat = line1[3];
                String move = line1 [4];

                //masuk masukin ke objek
                ArrayList<Double> stats = new ArrayList<Double>();
                String[] arrofstats = stat.split(",",7);
                for(String a : arrofstats){
                    Double d = Double.parseDouble(a);
                    // System.out.println(d);
                    stats.add(d);
                }
                Stats basestats = new Stats(stats.get(0), stats.get(1),stats.get(2),stats.get(3),stats.get(4),stats.get(5));

                ArrayList<ElementType> eltype = new ArrayList<ElementType>();
                String[] arrofel = element.split(",",7);
                for (String a : arrofel){
                    switch (a){
                        case ("NORMAL"):
                            eltype.add(ElementType.NORMAL);
                            break;
                        case ("FIRE"):
                            eltype.add(ElementType.FIRE);
                            break;  
                        case ("WATER"):
                            eltype.add(ElementType.WATER);
                            break; 
                        case ("GRASS"):
                            eltype.add(ElementType.GRASS);
                            break; 
                    }
                }

                String[] arrofmov = move.split(",",7);
                ArrayList<Move> monsmove = new ArrayList<Move>();
                for(int i = 0; i < arrofmov.length; i++){
                    Move asli = listMoves.get(Integer.valueOf(arrofmov[i])-1);
                    Move copy = (Move)asli.clone();
                    monsmove.add(copy);
                }

                //bikin object
                Monster baru = new Monster(name, eltype, basestats, monsmove);
                listMonster.add(baru);

                //nyalain config element type effectivity
                Effectivity.configEffectivity();

            }
            
            
        }
        catch (Exception e){
            System.out.println("ERROR");
        }
        
        ElementEffectivityKey a = new ElementEffectivityKey(ElementType.FIRE, ElementType.WATER);
        
        System.out.println( Effectivity.getEffectivity(a));
    }
}