package com.monstersaku;
import com.monstersaku.util.*;
import java.io.File;
import java.util.*;



public class Main {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));

    public static void main(String[] args) {
        //atribut untuk nyimpen bacaan csv
        ArrayList<Move> listMoves = new ArrayList<>();
        ArrayList<Monster> listMonster = new ArrayList<>();
        HashMap<ElementEffectivityKey,Double> map = new HashMap<>();

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
                
            }   

            CSVReader reader2 = new CSVReader(new File(Main.class.getResource("configs/element-type-effectivity-chart.csv").toURI()), ";");
            
            reader2.setSkipHeader(true);
            List<String[]> lines2 = reader2.read();
            
            for (String [] line2 : lines2){
                String source = line2[0];
                String target = line2[1];
                
                Double x = Double.parseDouble(line2[2]);          
                ElementType s = ElementType.NORMAL;
                ElementType t = ElementType.NORMAL;
                switch (source){
                    case ("NORMAL"):
                        s = ElementType.NORMAL;
                        break;
                    case ("FIRE"):
                        s = ElementType.FIRE;
                        break;  
                    case ("WATER"):
                        s = ElementType.WATER;
                        break; 
                    case ("GRASS"):
                        s = ElementType.GRASS;
                        break; 
                }
                switch (target){
                    case ("NORMAL"):
                        t = ElementType.NORMAL;
                        break;
                    case ("FIRE"):
                        t = ElementType.FIRE;
                        break;  
                    case ("WATER"):
                        t = ElementType.WATER;
                        break; 
                    case ("GRASS"):
                        t = ElementType.GRASS;
                        break; 
                }
                ElementEffectivityKey source_target = new ElementEffectivityKey(s,t);
                map.put(source_target, x);
            }
            
        }
        catch (Exception e){
            System.out.println("ERROR");
        } 


        //START GAME 
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter player 1 name : ");
        String player1name = scan.nextLine();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.println("Enter player 2 name : ");
        String player2name = scan.nextLine();
        System.out.println("Player 1 name : " + player1name);
        System.out.println("Player 2 name : " + player2name);

        //RANDOM MONSTER
        Random rand = new Random();
        Integer upperbound = listMonster.size();
        ArrayList<Monster> listOfPlayer1Monsters = new ArrayList<Monster>();
        for(int i = 0; i < 6; i++){
            Integer randommonster = rand.nextInt(upperbound);
            listOfPlayer1Monsters.add(listMonster.get(randommonster));
        }
        ArrayList<Monster> listOfPlayer2Monsters = new ArrayList<Monster>();
        for(int i = 0; i < 6; i++){
            Integer randommonster = rand.nextInt(upperbound);
            listOfPlayer2Monsters.add(listMonster.get(randommonster));
        }
        Player p1 = new Player(player1name, listOfPlayer1Monsters);
        Player p2 = new Player(player2name, listOfPlayer2Monsters);
        p1.printMonsters();
        p2.printMonsters();
        int turn = 0;
        boolean p1Lose = false;
        boolean p2Lose = false;
        Monster p1ActiveMons = p1.getListMonster().get(0);
        Monster p2ActiveMons = p2.getListMonster().get(0);



        //loop gameplay
        while (!p1Lose && !p2Lose) {
            turn++;
            System.out.printf("Player 1 Active Monster : %s%n", p1ActiveMons.getName());
            System.out.println("Select action : ");
            System.out.println("1. Move!!!");
            System.out.println("2. Switch!!!");
            int select1 = scan.nextInt();
                if(select1 == 1){
                    System.out.printf("%s move : %n", p1ActiveMons.getName());
                    for (int i = 0; i < p1ActiveMons.getMoves().size(); i++){
                        System.out.printf("%s%n", p1ActiveMons.getMoves().get(i).getName());
                    }
                }
                else if (select1 == 2){
                    p1.printMonsters();
                    System.out.println("Choose Id Monster : ");
                    int sel = scan.nextInt();
                    p1ActiveMons = p1.getListMonster().get(sel-1);
                    System.out.printf("Player 1 Active Monster : %s%n", p1ActiveMons.getName());
                }
        }
        scan.close();
    }
}