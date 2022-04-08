package com.monstersaku;
import com.monstersaku.util.*;
import java.io.File;
import java.util.*;


public class Main {
    //atribut untuk nyimpen bacaan csv
    private static ArrayList<Move> listMoves = new ArrayList<>();
    private static ArrayList<Monster> listMonster = new ArrayList<>();
    private static Effectivity listEffectivity = new Effectivity();

    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));

    public static void main(String[] args) {
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
                String target = line[7];
                
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

                    StatusMove a = new StatusMove(id, name, elType, accuracy, priority, ammunition,target, moveStatus, stat);

                    listMoves.add(a);
                }
                else if (moveType.equals("NORMAL")){
                    int effect = Integer.parseInt(line[8]);

                    NormalMove b = new NormalMove(id, name, elType, accuracy, priority, ammunition,target, effect);

                    listMoves.add(b);
                }
                else if (moveType.equals("SPECIAL")){
                    int effect = Integer.parseInt(line[8]);

                    SpecialMove c = new SpecialMove(id, name, elType, accuracy, priority, ammunition, target, effect);

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
                DefaultMove dm = new DefaultMove();
                monsmove.add(dm);

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
                listEffectivity.add(source_target, x);
            }
            
        }
        catch (Exception e){
            System.out.println("ERROR");
        } 

        System.out.println("WELCOME TO BERTARUNG MOSNTER");
        System.out.println(" ");
        System.out.println("MENU PERTARUNGAN");
        System.out.println("==============================");
        System.out.println("1. START GAME");
        System.out.println("2. HELP");
        System.out.println("3. VIEW MONSTER INFO");
        System.out.println("4. VIEW GAME INFO");
        System.out.println("5. EXIT");
        System.out.println("==============================");
        System.out.println("Masukkan angka: ");

        Scanner aplikasi = new Scanner(System.in);
        int menu = aplikasi.nextInt();

        boolean gameOn = false;

        while (menu != 5){
            if (menu == 1){
                //START GAME
                gameOn = true;
                Scanner scan = new Scanner(System.in);
                System.out.println("GAME START");
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
                    Monster originMonster1 = listMonster.get(randommonster);
                    while (listOfPlayer1Monsters.contains(originMonster1)){
                        randommonster = rand.nextInt(upperbound);
                        originMonster1 = listMonster.get(randommonster);
                    }    
                        //Monster copyMonster = (Monster)originMonster.clone();
                    listOfPlayer1Monsters.add(originMonster1);
                }
                ArrayList<Monster> listOfPlayer2Monsters = new ArrayList<Monster>();
                for(int i = 0; i < 6; i++){
                    Integer randommonster = rand.nextInt(upperbound);
                    Monster originMonster2 = listMonster.get(randommonster);
                    while ((listOfPlayer1Monsters.contains(originMonster2)) || (listOfPlayer2Monsters.contains(originMonster2))){
                        randommonster = rand.nextInt(upperbound);
                        originMonster2 = listMonster.get(randommonster);
                    }  
                        //Monster copyMonster = (Monster)originMonster.clone();
                    listOfPlayer2Monsters.add(originMonster2);
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



                // declare variable loop gameplaye
                boolean p1PilihanMoveValid = true;
                boolean p2PilihanMoveValid = true;
                int p1PilMove = -1;
                int p2PilMove = -1;

                int select1;
                int select2;

                //loop gameplay
                while (!p1Lose && !p2Lose) {

                    p1PilihanMoveValid = false;
                    p2PilihanMoveValid = false;
                    boolean p1BisaMove = true;
                    boolean p2BisaMove = true;

                    turn++;
                    /* Ngecek semua monster p1 dan p2 status condition sleep
                        setiap ada monster yang condition sleep
                        kurangin atribut sleepCounternya
                        caranya monster.setSleepCounter(monster.getSleepCounter() - 1);
                        kalo udah di angka 0 sleep counternya, ubah monster isSleep nya jadi ke false
                    */
                    for (int i = 0; i < p1.getJumlahMonster(); i++){
                        if (p1.getListMonster().get(i).getIsSleep()){   
                            p1.getListMonster().get(i).setSleepCounter(p1.getListMonster().get(i).getSleepCounter()-1);
                            if (p1.getListMonster().get(i).getSleepCounter() == 0){
                                p1.getListMonster().get(i).setIsSleep(false);
                            }
                        }
                        
                    }
                    for (int i = 0; i < p2.getJumlahMonster(); i++){
                        if (p2.getListMonster().get(i).getIsSleep()){   
                            p2.getListMonster().get(i).setSleepCounter(p2.getListMonster().get(i).getSleepCounter()-1);
                            if (p2.getListMonster().get(i).getSleepCounter() == 0){
                                p2.getListMonster().get(i).setIsSleep(false);
                            }
                        }
                    }

                    /* Ngecek semua monster p1 dan p2 ada yang Burn apa engga,
                        setiap ada yang burn, kurangin HP sesuai ketentuan Burn 
                    
                    */
                    for (int i = 0; i < p1.getJumlahMonster(); i++){
                        if (p1.getListMonster().get(i).getIsBurn()){   
                            StatusCondition.Burn(p1.getListMonster().get(i));
                        }    
                    }
                    for (int i = 0; i < p2.getJumlahMonster(); i++){
                        if (p2.getListMonster().get(i).getIsBurn()){   
                            StatusCondition.Burn(p2.getListMonster().get(i));
                        }  
                    }

                    /* Ngecek semua monster p1 dan p2 ada yang poison apa engga,
                        setiap ada yang poison, kurangin HP sesuai ketentuan poison 
                    
                    */
                    for (int i = 0; i < p1.getJumlahMonster(); i++){
                        if (p1.getListMonster().get(i).getIsPoison()){   
                            StatusCondition.Poison(p1.getListMonster().get(i));
                        }    
                    }
                    for (int i = 0; i < p2.getJumlahMonster(); i++){
                        if (p2.getListMonster().get(i).getIsPoison()){   
                            StatusCondition.Poison(p2.getListMonster().get(i));
                        }  
                    }



                    // PLAYER 1 nentuin TURN (BELUM EKSEKUSI)
                    boolean turnFinished = false;
                    while(!turnFinished){
                        System.out.printf("Player 1 Active Monster : %s%n", p1ActiveMons.getName());
                        System.out.println("Select action : ");
                        System.out.println("1. Move!!!");
                        System.out.println("2. Switch!!!");
                        System.out.println("3. View Current Monster's Status");
                        select1 = scan.nextInt();
                        // move
                        if(select1 == 1){
                            // ngecek aktive monster sleep atau paralyzed apa engga
                            if (p1ActiveMons.getIsSleep()) {
                                // ga terjadi apa apa tapi tetep dihitung melakukan turn (asumsi)
                                System.out.println("Monster yang aktif sedang sleep, tidak bisa melakukan move, sekarang giliran Player lain turn");
                            }
                            else {
                                // ngecek paralyzed
                                if (p1ActiveMons.getIsParalyze()) {
                                    // ngerandom 25% bisa move apa engga, nilai true falsenya masukin ke variable p1BisaMove
                                    int rand_int = rand.nextInt(4);
                                    if (rand_int == 0){
                                        p1BisaMove = false;
                                    }
                                    
                                }
                                if (p1BisaMove) {
                                    System.out.printf("%s move : %n", p1ActiveMons.getName());
                                    for (int i = 0; i < p1ActiveMons.getMoves().size(); i++){
                                        System.out.printf("%s%n", p1ActiveMons.getMoves().get(i).getName());
                                    }

                                    // milih move
                                    
                                    while (!p1PilihanMoveValid) {
                                        // input pilihan move
                                        System.out.printf("Pilih Move : ");
                                        String pilmove = scan.nextLine();
                                        // ngecek type move
                                        for (int i = 0; i < p1ActiveMons.getMoves().size(); i++){
                                            if (p1ActiveMons.getMoves().get(i).getName().equals(pilmove)){
                                                if (p1ActiveMons.getMoves().get(i).getAmmunition() ==0){
                                                    System.out.println("Amunisi habis.");
                                                }
                                                else{
                                                    p1PilMove = i;
                                                    p1PilihanMoveValid = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Anda tidak bisa move karena efek 25% paralyze aktif");
                                }
                            }
                            turnFinished = true;
                        }

                        // switch pokemon
                        else if (select1 == 2){
                            p1.printMonsters();
                            System.out.println("Choose Id Monster : ");
                            int sel = scan.nextInt();
                            
                            // bikin reset status buff untuk monster yang diswitch

                            // ngerubah aktif monster
                            p1ActiveMons = p1.getListMonster().get(sel-1);
                            System.out.printf("Player 1 Active Monster : %s%n", p1ActiveMons.getName());
                            turnFinished = true;
                        }
                        //view current monster status
                        else if (select1 == 3){
                            System.out.println(p2ActiveMons.getName() + "'s " + "Current Stats");
                            System.out.println("Health Point: " + p1ActiveMons.getStats().getHealthPoint());
                            System.out.println("Attack: " + p1ActiveMons.getStats().getAttack());
                            System.out.println("Defense: " + p1ActiveMons.getStats().getDefense());
                            System.out.println("Special Attack: " + p1ActiveMons.getStats().getSpesialAttack());
                            System.out.println("Special Defense: " + p1ActiveMons.getStats().getSpesialDefense());
                            System.out.println("Speed: " + p1ActiveMons.getStats().getSpeed());
                        }
                    }

                    

                    // PLAYER 2 nentuin TURN (BELUM EKSEKUSI)
                    turnFinished = false;
                    while (!turnFinished){
                        System.out.printf("Player 2 Active Monster : %s%n", p2ActiveMons.getName());
                        System.out.println("Select action : ");
                        System.out.println("1. Move!!!");
                        System.out.println("2. Switch!!!");
                        System.out.println("3. View Current Monster's Status");
                        select2 = scan.nextInt();
                        // move
                        if(select2 == 1){
                            // ngecek aktive monster sleep atau paralyzed apa engga
                            if (p2ActiveMons.getIsSleep()) {
                                // ga terjadi apa apa tapi tetep dihitung melakukan turn (asumsi)
                                System.out.println("Monster yang aktif sedang sleep, tidak bisa melakukan move, sekarang giliran Player lain turn");
                            }
                            else {
                                // ngecek paralyzed
                                if (p2ActiveMons.getIsParalyze()) {
                                    // ngerandom 25% bisa move apa engga, nilai true falsenya masukin ke variable p1BisaMove
                                    int rand_int = rand.nextInt(4);
                                    if (rand_int == 0){
                                        p2BisaMove = false;
                                    }
                                }
                                
                                if (p2BisaMove) {
                                    System.out.printf("%s move : %n", p2ActiveMons.getName());
                                    for (int i = 0; i < p2ActiveMons.getMoves().size(); i++){
                                        System.out.printf("%s%n", p2ActiveMons.getMoves().get(i).getName());
                                    }

                                    // milih move
                                    
                                    while (!p2PilihanMoveValid) {
                                        // input pilihan move
                                        System.out.printf("Pilih Move : ");
                                        String pilmove = scan.nextLine();
                                        // ngecek type move
                                        for (int i = 0; i < p2ActiveMons.getMoves().size(); i++){
                                            if (p2ActiveMons.getMoves().get(i).getName().equals(pilmove)){
                                                if (p2ActiveMons.getMoves().get(i).getAmmunition() ==0){
                                                    System.out.println("Amunisi habis.");
                                                }
                                                else{
                                                    p2PilMove = i;
                                                    p2PilihanMoveValid = true;
                                                }
                                            }
                                        }

                                    }
                                }
                                else {
                                    System.out.println("Anda tidak bisa move karena efek 25% paralyze aktif");
                                }
                            } 
                            turnFinished=true;
                        }

                        // switch pokemon
                        else if (select2 == 2){
                            p2.printMonsters();
                            System.out.println("Choose Id Monster : ");
                            int sel = scan.nextInt();

                            // bikin reset status buff untuk monster yang diswitch

                            // ngerubah aktif monster
                            p2ActiveMons = p2.getListMonster().get(sel-1);
                            System.out.printf("Player 2 Active Monster : %s%n", p2ActiveMons.getName());
                            turnFinished = true;
                        }
                        else if(select2 == 3){
                            System.out.println(p2ActiveMons.getName() + "'s " + "Current Stats");
                            System.out.println("Health Point: " + p2ActiveMons.getStats().getHealthPoint());
                            System.out.println("Attack: " + p2ActiveMons.getStats().getAttack());
                            System.out.println("Defense: " + p2ActiveMons.getStats().getDefense());
                            System.out.println("Special Attack: " + p2ActiveMons.getStats().getSpesialAttack());
                            System.out.println("Special Defense: " + p2ActiveMons.getStats().getSpesialDefense());
                            System.out.println("Speed: " + p2ActiveMons.getStats().getSpeed());
                        }
                    }

                    

                    // PROSES EKSEKUSI PILIHAN KEDUA PLAYER

                    // kalo kedua player ngelakuin move
                    if ((p1PilihanMoveValid == true) && (p2PilihanMoveValid == true)) {
                        System.out.println("a");
                        // ngecek priority mana yang lebih gede
                        if (p1ActiveMons.getMoves().get(p1PilMove).getPriority() == p2ActiveMons.getMoves().get(p2PilMove).getPriority()){
                            System.out.println("AAAAAAAAAAAAAAAAAAAA");
                            /* Speed P1 >= Speed P2  */
                            if (p1ActiveMons.getStats().getSpeed() >= p2ActiveMons.getStats().getSpeed()){
                                System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang duluan");
                                p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                                if (!p2ActiveMons.getIsSleep()){
                                    System.out.println(p2ActiveMons.getName() + " milik Player 2 menyerang balik menggunakan" + p2ActiveMons.getMoves().get(p2PilMove).getName());
                                    if (p2ActiveMons.getMoves().get(p2PilMove) instanceof StatusMove){
                                        p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                    } else {
                                        p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                    }
                                } 
                                else if (p2ActiveMons.getIsParalyze()){
                                    /*Blom dibuat */
                                    System.out.println("Monster ini baru saha terkena efek paralyze dari move p1 sebelumnya");
                                    System.out.println("Efek 25% akan dirandom...");
                                }
                                System.out.println(p2ActiveMons.getName() + " dalam kondisi sleep");
                            }
                            /* Speed P1 < Speed P2  */
                            else if(p1ActiveMons.getStats().getSpeed() < p2ActiveMons.getStats().getSpeed()){
                                System.out.println(p2ActiveMons.getName() + " milik Player 2 menyerang duluan");
                                p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                if (!p1ActiveMons.getIsSleep()){
                                    System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang balik menggunakan" + p1ActiveMons.getMoves().get(p2PilMove).getName());
                                    if (p1ActiveMons.getMoves().get(p1PilMove) instanceof StatusMove){
                                        p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                                    } else {
                                        p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                    }
                                } else if (p1ActiveMons.getIsParalyze()){
                                    System.out.println("Monster ini baru saha terkena efek paralyze dari move p1 sebelumnya");
                                    System.out.println("Efek 25% akan dirandom...");
                                    /*Blom dibuat */
                                }
                                System.out.println(p1ActiveMons.getName() + " dalam kondisi sleep");
                            }

                        }
                        else if (p1ActiveMons.getMoves().get(p1PilMove).getPriority() > p2ActiveMons.getMoves().get(p2PilMove).getPriority()){
                            System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang duluan");
                            p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                            if (!p2ActiveMons.getIsSleep()){
                                System.out.println(p2ActiveMons.getName() + " menyerang milik Player 2 balik menggunakan" + p2ActiveMons.getMoves().get(p2PilMove));
                                if (p2ActiveMons.getMoves().get(p2PilMove) instanceof StatusMove){
                                    p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                } else {
                                    p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                }
                            } else if (p2ActiveMons.getIsParalyze()){
                                System.out.println("Monster ini baru saha terkena efek paralyze dari move p1 sebelumnya");
                                System.out.println("Efek 25% akan dirandom...");
                                /*Blom dibuat */
                            }
                            System.out.println(p2ActiveMons.getName() + " dalam kondisi sleep");
                        }
                        
                        else if (p1ActiveMons.getMoves().get(p1PilMove).getPriority() < p2ActiveMons.getMoves().get(p2PilMove).getPriority()){
                            System.out.println(p2ActiveMons.getName() + " milik Player 2 menyerang duluan");
                            p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                            if (!p1ActiveMons.getIsSleep()){
                                System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang balik menggunakan" + p1ActiveMons.getMoves().get(p2PilMove));
                                if (p1ActiveMons.getMoves().get(p1PilMove) instanceof StatusMove){
                                    p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                                } else {
                                    p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                }
                            } else if (p1ActiveMons.getIsParalyze()){
                                System.out.println("Monster ini baru saha terkena efek paralyze dari move p1 sebelumnya");
                                System.out.println("Efek 25% akan dirandom...");
                                /*Blom dibuat */
                            }
                            System.out.println(p1ActiveMons.getName() + " dalam kondisi sleep");
                        }
                            
                    }
                    // kalo p1 doang yang move, p2 engga (karena cuma ngeswitch pas milih turn, atau karena efek stats condition)
                    else if ((p1PilihanMoveValid == true) && (p2PilihanMoveValid == false)) {
                        System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang menggunakan " + p1ActiveMons.getMoves().get(p1PilMove));
                        p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                        System.out.println(p2ActiveMons.getName() + " terkena serangan " + p1ActiveMons.getMoves().get(p1PilMove));

                    }

                    // kalo p2 doang yang move, p1 engga (karena cuma ngeswitch pas milih turn, atau karena efek stats condition)
                    else if ((p2PilihanMoveValid == true) && (p1PilihanMoveValid == false)) {
                        System.out.println(p2ActiveMons.getName() + " milik Player 2 menyerang menggunakan " + p2ActiveMons.getMoves().get(p2PilMove));
                        p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                        System.out.println(p1ActiveMons.getName() + " terkena serangan " + p2ActiveMons.getMoves().get(p1PilMove));
                        
                    }

                    // ----------- NGECEK UDAH ADA YANG WIN APA BELUM --------------

                    // cek semua monster p1 dan p2 HP nya, pake counter jumlah yg masi idup

                    // kalo ada yang jumlahnya 0, berarti game selesai

                }
                scan.close();
            }
             else if (menu == 2){
                System.out.println("Permainan dimainkan oleh 2 player. Masing-masing player akan mendapatkan 6 monster yang diberikan secara acak.");
            }
             else if (menu == 3){
                if (gameOn = false){
                    System.out.println("balbalalwlwd");
                } else {
                    System.out.println("Menu tidak tersedia. Silahkan Start game terlebih dahulu.");
                }
            }
             else if (menu == 4){
    
            }
            System.out.println("MENU PERTARUNGAN");
            System.out.println("==============================");
            System.out.println("1. START GAME");
            System.out.println("2. HELP");
            System.out.println("3. VIEW MONSTER INFO");
            System.out.println("4. VIEW GAME INFO");
            System.out.println("5. EXIT");
            System.out.println("==============================");
            System.out.println("Masukkan angka: ");

            menu = aplikasi.nextInt();

        }
        System.out.println("Telah keluar dari aplikasi. Selamat Tinggal.");

        aplikasi.close();

        
    }
}