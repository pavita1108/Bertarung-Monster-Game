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
        Scanner scan = new Scanner(System.in);
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
                Monster baru = new Monster(id,name, eltype, basestats, monsmove);
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

        System.out.println("░██╗░░░░░░░██╗███████╗██╗░░░░░░█████╗░░█████╗░███╗░░░███╗███████╗  ████████╗░█████╗░");
        System.out.println("░██║░░██╗░░██║██╔════╝██║░░░░░██╔══██╗██╔══██╗████╗░████║██╔════╝  ╚══██╔══╝██╔══██╗");
        System.out.println("░╚██╗████╗██╔╝█████╗░░██║░░░░░██║░░╚═╝██║░░██║██╔████╔██║█████╗░░  ░░░██║░░░██║░░██║");
        System.out.println("░░████╔═████║░██╔══╝░░██║░░░░░██║░░██╗██║░░██║██║╚██╔╝██║██╔══╝░░  ░░░██║░░░██║░░██║");
        System.out.println("░░╚██╔╝░╚██╔╝░███████╗███████╗╚█████╔╝╚█████╔╝██║░╚═╝░██║███████╗  ░░░██║░░░╚█████╔╝");
        System.out.println("░░░╚═╝░░░╚═╝░░╚══════╝╚══════╝░╚════╝░░╚════╝░╚═╝░░░░░╚═╝╚══════╝  ░░░╚═╝░░░░╚════╝░");
        System.out.println("████████████████████████████████████████████████████████▀████████████████████████████████████████████████");
        System.out.println("█▄─▄─▀█▄─▄▄─█▄─▄▄▀█─▄─▄─██▀▄─██▄─▄▄▀█▄─██─▄█▄─▀█▄─▄█─▄▄▄▄███▄─▀█▀─▄█─▄▄─█▄─▀█▄─▄█─▄▄▄▄█─▄─▄─█▄─▄▄─█▄─▄▄▀█");
        System.out.println("██─▄─▀██─▄█▀██─▄─▄███─████─▀─███─▄─▄██─██─███─█▄▀─██─██▄─████─█▄█─██─██─██─█▄▀─██▄▄▄▄─███─████─▄█▀██─▄─▄█");
        System.out.println("▀▄▄▄▄▀▀▄▄▄▄▄▀▄▄▀▄▄▀▀▄▄▄▀▀▄▄▀▄▄▀▄▄▀▄▄▀▀▄▄▄▄▀▀▄▄▄▀▀▄▄▀▄▄▄▄▄▀▀▀▄▄▄▀▄▄▄▀▄▄▄▄▀▄▄▄▀▀▄▄▀▄▄▄▄▄▀▀▄▄▄▀▀▄▄▄▄▄▀▄▄▀▄▄▀");
        System.out.println(" ");
        System.out.println("MENU PERTARUNGAN");
        System.out.println("==============================");
        System.out.println("1. START GAME");
        System.out.println("2. HELP");
        System.out.println("3. EXIT");
        System.out.println("==============================");
        System.out.println("Masukkan angka: ");

        Scanner aplikasi = new Scanner(System.in);
        int menu = aplikasi.nextInt();

        boolean gameOn = false;
        
        while (menu != 3){
            if (menu == 1){
                //START GAME
                gameOn = true;
                boolean endgame = false;
                
                System.out.println("GAME START");
                System.out.println("");
                System.out.println("Enter player 1 name : ");
                String player1name = scan.nextLine();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                System.out.println("Enter player 2 name : ");
                String player2name = scan.nextLine();
                System.out.println("");
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
                System.out.println("==================== PEMBAGIAN MONSTER ====================\n");
                p1.printMonsters();
                p2.printMonsters();
                int turn = 0;
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
                while (!endgame) {

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
                            System.out.println("Monster " + p1.getListMonster().get(i).getName() + " milik player 1 sedang sleep, karena turn berputar status sleep berkurang 1");
                            if (p1.getListMonster().get(i).getSleepCounter() == 0){
                                p1.getListMonster().get(i).setIsSleep(false);
                                System.out.println("Monster " + p1.getListMonster().get(i).getName() + " milik player 1 sudah bangkit");
                            }
                        }
                        
                    }
                    for (int i = 0; i < p2.getJumlahMonster(); i++){
                        if (p2.getListMonster().get(i).getIsSleep()){   
                            p2.getListMonster().get(i).setSleepCounter(p2.getListMonster().get(i).getSleepCounter()-1);
                            System.out.println("Monster " + p2.getListMonster().get(i).getName() + " milik player 2 sedang sleep, karena turn berputar status sleep berkurang 1");
                            if (p2.getListMonster().get(i).getSleepCounter() == 0){
                                p2.getListMonster().get(i).setIsSleep(false);
                                System.out.println("Monster " + p1.getListMonster().get(i).getName() + " milik player 1 sudah bangkit");
                            }
                        }
                    }

                    /* Ngecek semua monster p1 dan p2 ada yang Burn apa engga,
                        setiap ada yang burn, kurangin HP sesuai ketentuan Burn 
                    
                    */
                    for (int i = 0; i < p1.getJumlahMonster(); i++){
                        if ((p1.getListMonster().get(i).getIsBurn()) && (p1.getListMonster().get(i).getStats().getHealthPoint() > 0)){   
                            StatusCondition.Burn(p1.getListMonster().get(i));
                            System.out.println("Monster " + p1.getListMonster().get(i).getName() + " milik player 1 berkurang HP karena efek burn");
                            if (p1.getListMonster().get(i).getStats().getHealthPoint() <= 0) {
                                System.out.println("Monster " + p1.getListMonster().get(i).getName() + " milik player 1 mati karena efek burn");
                                p1.getListMonster().get(i).getStats().setHealtPoint(0);
                                p1.getListMonster().get(i).setIsDead(true);
                                p1.getListMonster().get(i).setIsBurn(false);
                            }
                        }    
                    }
                    for (int i = 0; i < p2.getJumlahMonster(); i++){
                        if ((p2.getListMonster().get(i).getIsBurn()) && (p2.getListMonster().get(i).getStats().getHealthPoint() > 0)){   
                            StatusCondition.Burn(p2.getListMonster().get(i));
                            System.out.println("Monster " + p2.getListMonster().get(i).getName() + " milik player 2 berkurang HP karena efek burn");
                            if (p2.getListMonster().get(i).getStats().getHealthPoint() <= 0) {
                                System.out.println("Monster " + p2.getListMonster().get(i).getName() + " milik player 2 mati karena efek burn");
                                p2.getListMonster().get(i).getStats().setHealtPoint(0);
                                p2.getListMonster().get(i).setIsDead(true);
                                p2.getListMonster().get(i).setIsBurn(false);
                            }
                        }  
                    }

                    /* Ngecek semua monster p1 dan p2 ada yang poison apa engga,
                        setiap ada yang poison, kurangin HP sesuai ketentuan poison 
                    
                    */
                    for (int i = 0; i < p1.getJumlahMonster(); i++){
                        if ((p1.getListMonster().get(i).getIsPoison()) && (p1.getListMonster().get(i).getStats().getHealthPoint() > 0)){   
                            StatusCondition.Poison(p1.getListMonster().get(i));
                            System.out.println("Monster " + p1.getListMonster().get(i).getName() + " milik player 1 berkurang HP karena efek poison");
                            if (p1.getListMonster().get(i).getStats().getHealthPoint() <= 0) {
                                System.out.println("Monster " + p1.getListMonster().get(i).getName() + " milik player 1 mati karena efek poison");
                                p1.getListMonster().get(i).getStats().setHealtPoint(0);
                                p1.getListMonster().get(i).setIsDead(true);
                                p1.getListMonster().get(i).setIsPoison(false);
                            }
                        }    
                    }
                    for (int i = 0; i < p2.getJumlahMonster(); i++){
                        if ((p2.getListMonster().get(i).getIsPoison()) && (p2.getListMonster().get(i).getStats().getHealthPoint() > 0)){   
                            StatusCondition.Poison(p2.getListMonster().get(i));
                            System.out.println("Monster " + p2.getListMonster().get(i).getName() + " milik player 2 berkurang HP karena efek poison");
                            if (p2.getListMonster().get(i).getStats().getHealthPoint() <= 0) {
                                System.out.println("Monster " + p2.getListMonster().get(i).getName() + " milik player 2 mati karena efek poison");
                                p2.getListMonster().get(i).getStats().setHealtPoint(0);
                                p2.getListMonster().get(i).setIsDead(true);
                                p2.getListMonster().get(i).setIsPoison(false);
                            }
                        }  
                    }

                    System.out.println("\n==================== TURN KE- "+ turn +" ====================\n");

                    // PLAYER 1 nentuin TURN (BELUM EKSEKUSI)
                    boolean turnFinished = false;
                    while(!turnFinished){
                        System.out.printf("Player 1 Active Monster : %s%n", p1ActiveMons.getName());
                        System.out.println("1. Move!!!");
                        System.out.println("2. Switch!!!");
                        System.out.println("3. View Current Monster's Status");
                        System.out.println("4. View Current Game Info");
                        System.out.printf("Player 1 select action : ");
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
                                    StatusCondition.Paralyze(p1ActiveMons);
                                    int rand_int = rand.nextInt(4);
                                    if (rand_int == 0){
                                        p1BisaMove = false;
                                    }
                                    
                                }
                                if (p1BisaMove) {
                                    System.out.printf("%s move : %n", p1ActiveMons.getName());
                                    for (int i = 0; i < p1ActiveMons.getMoves().size(); i++){
                                        System.out.printf("%d. %s%n", i+1,p1ActiveMons.getMoves().get(i).getName());
                                    }

                                    // milih move
                                    while (!p1PilihanMoveValid) {
                                        // input pilihan move
                                        System.out.printf("Pilih Move : ");
                                        int pilmove = scan.nextInt();
                                        // ngecek type move
                                        if(pilmove < 1 || pilmove > p1ActiveMons.getMoves().size()){
                                            System.out.println("salah pilih");
                                        }
                                        else if (p1ActiveMons.getMoves().get(pilmove -1 ).getAmmunition() ==0){
                                            System.out.println("Amunisi habis.");
                                        }
                                        else{
                                            p1PilMove = pilmove-1;
                                            p1PilihanMoveValid = true;
                                            System.out.println(p1ActiveMons.getMoves().get(p1PilMove).getName() + " telah dipilih");
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
                            if (p1.getJumlahMonster() == 1){
                                System.out.println("Udah tinggal 1 monsternya :( ga bisa switch");
                            }
                            else{
                                p1.printMonsters();
                                System.out.println("Choose Id Monster : ");
                                int sel = scan.nextInt();
                                
                                // bikin reset status buff untuk monster yang diswitch

                                // ngerubah aktif monster
                                p1ActiveMons = p1.getListMonster().get(sel-1);
                                System.out.printf("Player 1 Active Monster : %s%n", p1ActiveMons.getName());
                                
                            }
                            turnFinished = true;
                            
                        }
                        //view current monster status
                        else if (select1 == 3){
                            p1ActiveMons.printStats();
                        }
                        else if (select1 == 4){
                            System.out.printf("Turn skrng : %d%n",turn );

                            System.out.println("=====================================");
                            System.out.println(p1.getNama() + "'s Information");
                            System.out.println("=====================================");
                            System.out.println("Monster Player 1 yg aktif : "+ p1ActiveMons.getName());
                            p1ActiveMons.printStats();
                            System.out.println("=====================================");
                            System.out.println("Monster Player 1 yg tidak aktif :");
                            System.out.println("");
                            for(int a = 0; a < p1.getListMonster().size() ; a++){
                                if(p1.getListMonster().get(a).getId() != p1ActiveMons.getId()){
                                    System.out.println(p1.getListMonster().get(a).getName());
                                    p1.getListMonster().get(a).printStats();
                                    System.out.println("=====================================");
                                }
                            }
                            System.out.println("");

                            System.out.println("=====================================");
                            System.out.println(p2.getNama() + "'s Information");
                            System.out.println("=====================================");
                            System.out.println("Monster Player 2 yg aktif : "+ p2ActiveMons.getName());
                            p2ActiveMons.printStats();
                            System.out.println("=====================================");
                            System.out.println("Monster Player 2 yg tidak aktif :");
                            System.out.println("");
                            for(int a = 0; a < p2.getListMonster().size() ; a++){
                                if(p2.getListMonster().get(a).getId() != p2ActiveMons.getId()){
                                    System.out.println(p2.getListMonster().get(a).getName());
                                    p2.getListMonster().get(a).printStats();
                                    System.out.println("=====================================");
                                }
                            }
                            System.out.println("");
                        }
                    }

                    

                    // PLAYER 2 nentuin TURN (BELUM EKSEKUSI)
                    turnFinished = false;
                    while (!turnFinished){
                        System.out.printf("Player 2 Active Monster : %s%n", p2ActiveMons.getName());
                        System.out.println("1. Move!!!");
                        System.out.println("2. Switch!!!");
                        System.out.println("3. View Current Monster's Status");
                        System.out.println("4. View Current Game Info");
                        System.out.printf("Player 2 select action : ");
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
                                    StatusCondition.Paralyze(p2ActiveMons);
                                    int rand_int = rand.nextInt(4);
                                    if (rand_int == 0){
                                        p2BisaMove = false;
                                    }
                                }
                                
                                if (p2BisaMove) {
                                    System.out.printf("%s move : %n", p2ActiveMons.getName());
                                    for (int i = 0; i < p2ActiveMons.getMoves().size(); i++){
                                        System.out.printf("%d. %s%n", i+1,p2ActiveMons.getMoves().get(i).getName());
                                    }

                                    // milih move
                                    while (!p2PilihanMoveValid) {
                                        // input pilihan move
                                        System.out.printf("Pilih Move : ");
                                        int pilmove = scan.nextInt();
                                        // ngecek type move
                                        if(pilmove < 1 || pilmove > p2ActiveMons.getMoves().size()){
                                            System.out.println("salah pilih");
                                        }
                                        else if (p2ActiveMons.getMoves().get(pilmove -1 ).getAmmunition() ==0){
                                            System.out.println("Amunisi habis.");
                                        }
                                        else{
                                            p2PilMove = pilmove-1;
                                            p2PilihanMoveValid = true;
                                            System.out.println(p2ActiveMons.getMoves().get(p2PilMove).getName() + " telah dipilih");
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
                            if (p2.getJumlahMonster() == 1){
                                System.out.println("Udah tinggal 1 monsternya :( ga bisa switch");
                            }
                            else{
                                p2.printMonsters();
                                System.out.println("Choose Id Monster : ");
                                int sel = scan.nextInt();
                                
                                // bikin reset status buff untuk monster yang diswitch

                                // ngerubah aktif monster
                                p2ActiveMons = p2.getListMonster().get(sel-1);
                                System.out.printf("Player 1 Active Monster : %s%n", p2ActiveMons.getName());
                                
                            }
                            turnFinished = true;
                        }
                        else if(select2 == 3){
                            p2ActiveMons.printStats();
                        }
                        else if (select2 == 4){
                            System.out.printf("Turn skrng : %d%n",turn );

                            System.out.println("=====================================");
                            System.out.println(p1.getNama() + "'s Information");
                            System.out.println("=====================================");
                            System.out.println("Monster Player 1 yg aktif : "+ p1ActiveMons.getName());
                            p1ActiveMons.printStats();
                            System.out.println("=====================================");
                            System.out.println("Monster Player 1 yg tidak aktif :");
                            System.out.println("");
                            for(int a = 0; a < p1.getListMonster().size() ; a++){
                                if(p1.getListMonster().get(a).getId() != p1ActiveMons.getId()){
                                    System.out.println(p1.getListMonster().get(a).getName());
                                    p1.getListMonster().get(a).printStats();
                                    System.out.println("=====================================");
                                }
                            }
                            System.out.println("");

                            System.out.println("=====================================");
                            System.out.println(p2.getNama() + "'s Information");
                            System.out.println("=====================================");
                            System.out.println("Monster Player 2 yg aktif : "+ p2ActiveMons.getName());
                            p2ActiveMons.printStats();
                            System.out.println("=====================================");
                            System.out.println("Monster Player 2 yg tidak aktif :");
                            System.out.println("");
                            for(int a = 0; a < p2.getListMonster().size() ; a++){
                                if(p2.getListMonster().get(a).getId() != p2ActiveMons.getId()){
                                    System.out.println(p2.getListMonster().get(a).getName());
                                    p2.getListMonster().get(a).printStats();
                                    System.out.println("=====================================");
                                }
                            }
                            System.out.println("");
                        }
                    }

                    

                    // PROSES EKSEKUSI PILIHAN KEDUA PLAYER

                    // kalo kedua player ngelakuin move
                    if ((p1PilihanMoveValid == true) && (p2PilihanMoveValid == true)) {
                        // ngecek priority mana yang lebih gede
                        if (p1ActiveMons.getMoves().get(p1PilMove).getPriority() > p2ActiveMons.getMoves().get(p2PilMove).getPriority() || (p1ActiveMons.getMoves().get(p1PilMove).getPriority() == p2ActiveMons.getMoves().get(p2PilMove).getPriority() && p1ActiveMons.getStats().getSpeed() >= p2ActiveMons.getStats().getSpeed())){
                            System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang duluan");
                            p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                            //cek mati ato nggak
                            if (p2ActiveMons.getIsDead()){
                                p2.setJumlahMonster(p2.getJumlahMonster()-1);
                                System.out.printf("%s is Dead :(%n", p2ActiveMons.getName());
                                if (!p2.isLose()){
                                    p2.printMonsters();
                                    System.out.println("Choose New Monster Id: ");
                                    int choosenId = scan.nextInt();
                                    while (p2.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0 || (p2.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p2.getJumlahMonster() != 1)){
                                        if (p2.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0){   
                                            System.out.println("Monster sudah mati, silahkan pilih monster lain.");
                                            System.out.println("Choose Monster Id : ");
                                            choosenId = scan.nextInt();
                                        }
                                        else if (p2.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p2.getJumlahMonster() != 1){
                                            System.out.println("Monster sedang tertidur, silahkan pilih monster lain.");
                                            System.out.println("Choose Monster Id : ");
                                            choosenId = scan.nextInt();
                                        }
                                    }
                                    p2ActiveMons = p2.getListMonster().get(choosenId-1);
                                    System.out.printf("%s's Active Monster : %s%n", p2.getNama(), p2ActiveMons.getName());
                                }
                                else{
                                    endgame = true;
                                }
                            }
                            //kalo ga mati cek lagi tidur ato paralyze ga
                            else{
                                if (p2ActiveMons.getIsSleep()){
                                    System.out.println(p2ActiveMons.getName() + " dalam kondisi sleep");
                                } 
                                else if (p2ActiveMons.getIsParalyze()){
                                    System.out.println("Monster ini baru saha terkena efek paralyze dari move p1 sebelumnya");
                                    System.out.println("Efek 25% akan dirandom...");
                                    int rand_int = rand.nextInt(4);
                                    if (rand_int == 0){
                                        System.out.println("Monster terkena stunt");
                                    }
                                    else{
                                        System.out.println("Monster tidak kena stunt");
                                        p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                    }
                                }
                                else{
                                    System.out.println(p2ActiveMons.getName() + " milik Player 2 menyerang balik menggunakan" + p2ActiveMons.getMoves().get(p2PilMove).getName());
                                    p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                                    if (p1ActiveMons.getIsDead()){
                                        System.out.printf("%s is Dead :(%n", p1ActiveMons.getName());
                                        if (!p1.isLose()){
                                            p1.printMonsters();
                                            System.out.println("Choose New Monster Id: ");
                                            int choosenId = scan.nextInt();
                                            while (p1.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0 || (p1.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p1.getJumlahMonster() != 1)){
                                                if (p1.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0){   
                                                    System.out.println("Monster sudah mati, silahkan pilih monster lain.");
                                                    System.out.println("Choose Monster Id : ");
                                                    choosenId = scan.nextInt();
                                                }
                                                else if (p1.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p1.getJumlahMonster() != 1){
                                                    System.out.println("Monster sedang tertidur, silahkan pilih monster lain.");
                                                    System.out.println("Choose Monster Id : ");
                                                    choosenId = scan.nextInt();
                                                }
                                            }
                                            p1ActiveMons = p1.getListMonster().get(choosenId-1);
                                            System.out.printf("%s's Active Monster : %s%n", p1.getNama(), p1ActiveMons.getName());
                                        }
                                        else{
                                            endgame = true;
                                        }
                                    }
                                }
                            }
                        }
                        else if ((p1ActiveMons.getMoves().get(p1PilMove).getPriority() < p2ActiveMons.getMoves().get(p2PilMove).getPriority()) || (p1ActiveMons.getStats().getSpeed() < p2ActiveMons.getStats().getSpeed())){
                            System.out.println(p2ActiveMons.getName() + " milik Player 2 menyerang duluan");
                            p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                            if (p1ActiveMons.getIsDead()){
                                p1.setJumlahMonster(p1.getJumlahMonster()-1);
                                System.out.printf("%s is Dead :(%n", p1ActiveMons.getName());
                                if (!p1.isLose()){
                                    p1.printMonsters();
                                    System.out.println("Choose New Monster Id: ");
                                    int choosenId = scan.nextInt();
                                    while (p1.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0 || (p1.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p1.getJumlahMonster() != 1)){
                                        if (p1.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0){   
                                            System.out.println("Monster sudah mati, silahkan pilih monster lain.");
                                            System.out.println("Choose Monster Id : ");
                                            choosenId = scan.nextInt();
                                        }
                                        else if (p1.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p1.getJumlahMonster() != 1){
                                            System.out.println("Monster sedang tertidur, silahkan pilih monster lain.");
                                            System.out.println("Choose Monster Id : ");
                                            choosenId = scan.nextInt();
                                        }
                                    }
                                    p1ActiveMons = p1.getListMonster().get(choosenId-1);
                                    System.out.printf("%s's Active Monster : %s%n", p1.getNama(), p1ActiveMons.getName());
                                }
                                else{
                                    endgame = true;
                                }
                            }
                            else{
                                if (p1ActiveMons.getIsSleep()){
                                    System.out.println(p1ActiveMons.getName() + " dalam kondisi sleep");
                                } 
                                else if (p1ActiveMons.getIsParalyze()){
                                    System.out.println("Monster ini baru saha terkena efek paralyze dari move p1 sebelumnya");
                                    System.out.println("Efek 25% akan dirandom...");
                                    int rand_int = rand.nextInt(4);
                                    if (rand_int == 0){
                                        System.out.println("Monster terkena stunt");
                                    }
                                    else{
                                        System.out.println("Monster tidak kena stunt");
                                        p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);                                    
                                    }
                                }
                                else{
                                    System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang balik menggunakan" + p1ActiveMons.getMoves().get(p1PilMove).getName());
                                    p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                                    if (p2ActiveMons.getIsDead()){
                                        System.out.printf("%s is Dead :(%n", p2ActiveMons.getName());
                                        if (!p2.isLose()){
                                            p2.printMonsters();
                                            System.out.println("Choose New Monster Id: ");
                                            int choosenId = scan.nextInt();
                                            while (p2.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0 || (p2.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p2.getJumlahMonster() != 1)){
                                                if (p2.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0){   
                                                    System.out.println("Monster sudah mati, silahkan pilih monster lain.");
                                                    System.out.println("Choose Monster Id : ");
                                                    choosenId = scan.nextInt();
                                                }
                                                else if (p2.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p2.getJumlahMonster() != 1){
                                                    System.out.println("Monster sedang tertidur, silahkan pilih monster lain.");
                                                    System.out.println("Choose Monster Id : ");
                                                    choosenId = scan.nextInt();
                                                }
                                            }
                                            p2ActiveMons = p2.getListMonster().get(choosenId-1);
                                            System.out.printf("%s's Active Monster : %s%n", p2.getNama(), p2ActiveMons.getName());
                                        }
                                        else{
                                            endgame = true;
                                        }
                                    }
                                }
                            }
                            
                        }    
                    }
                    // kalo p1 doang yang move, p2 engga (karena cuma ngeswitch pas milih turn, atau karena efek stats condition)
                    else if ((p1PilihanMoveValid == true) && (p2PilihanMoveValid == false)) {
                        System.out.println(p1ActiveMons.getName() + " milik Player 1 menyerang menggunakan " + p1ActiveMons.getMoves().get(p1PilMove).getName());
                        p1ActiveMons.getMoves().get(p1PilMove).applyEffect(p1ActiveMons, p2ActiveMons, listEffectivity);
                        System.out.println(p2ActiveMons.getName() + " terkena serangan " + p1ActiveMons.getMoves().get(p1PilMove).getName());
                        if (p2ActiveMons.getIsDead()){
                            p2.setJumlahMonster(p2.getJumlahMonster()-1);
                            System.out.printf("%s is Dead :(%n", p2ActiveMons.getName());
                            if (!p2.isLose()){
                                p2.printMonsters();
                                System.out.println("Choose New Monster Id: ");
                                int choosenId = scan.nextInt();
                                while (p2.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0 || p2.getListMonster().get(choosenId-1).getSleepCounter() > 0){
                                    if (p2.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0){   
                                        System.out.println("Monster sudah mati, silahkan pilih monster lain.");
                                        System.out.println("Choose Monster Id : ");
                                        choosenId = scan.nextInt();
                                    }
                                    else if (p2.getListMonster().get(choosenId-1).getSleepCounter() > 0){
                                        System.out.println("Monster sedang tertidur, silahkan pilih monster lain.");
                                        System.out.println("Choose Monster Id : ");
                                        choosenId = scan.nextInt();
                                    }
                                }
                                p2ActiveMons = p2.getListMonster().get(choosenId-1);
                                System.out.printf("%s's Active Monster : %s%n", p2.getNama(), p2ActiveMons.getName());
                            }
                            else{
                                endgame = true;
                            }
                        }
                    }

                    // kalo p2 doang yang move, p1 engga (karena cuma ngeswitch pas milih turn, atau karena efek stats condition)
                    else if ((p2PilihanMoveValid == true) && (p1PilihanMoveValid == false)) {
                        System.out.println(p2ActiveMons.getName() + " milik Player 2 menyerang menggunakan " + p2ActiveMons.getMoves().get(p2PilMove).getName());
                        p2ActiveMons.getMoves().get(p2PilMove).applyEffect(p2ActiveMons, p1ActiveMons, listEffectivity);
                        System.out.println(p1ActiveMons.getName() + " terkena serangan " + p2ActiveMons.getMoves().get(p1PilMove).getName());
                        if (p1ActiveMons.getIsDead()){
                            p1.setJumlahMonster(p1.getJumlahMonster()-1);
                            System.out.printf("%s is Dead :(%n", p1ActiveMons.getName());
                            if (!p1.isLose()){
                                p1.printMonsters();
                                System.out.println("Choose New Monster Id: ");
                                int choosenId = scan.nextInt();
                                while (p1.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0 || (p1.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p1.getJumlahMonster() != 1)){
                                    if (p1.getListMonster().get(choosenId-1).getStats().getHealthPoint() <= 0){   
                                        System.out.println("Monster sudah mati, silahkan pilih monster lain.");
                                        System.out.println("Choose Monster Id : ");
                                        choosenId = scan.nextInt();
                                    }
                                    else if (p1.getListMonster().get(choosenId-1).getSleepCounter() > 0 && p1.getJumlahMonster() != 1){
                                        System.out.println("Monster sedang tertidur, silahkan pilih monster lain.");
                                        System.out.println("Choose Monster Id : ");
                                        choosenId = scan.nextInt();
                                    }
                                }
                                p1ActiveMons = p1.getListMonster().get(choosenId-1);
                                System.out.printf("%s's Active Monster : %s%n", p1.getNama(), p1ActiveMons.getName());
                            }
                            else{
                                endgame = true;
                            }
                        }
                    }
                    System.out.println("");

                    System.out.println("==================== INFO STATUS MONSTER PERGANTIAN PUTARAN TURN ====================\n");

                    System.out.println("Kondisi monster sekarang :");
                    System.out.println("Monster Player 1 : ");
                    p1ActiveMons.printStats();
                    System.out.println("Monster Player 2 : ");
                    p2ActiveMons.printStats();

                    if (p1.isLose()){
                        System.out.println("PLAYER 2 : "+p2.getNama()+ " MENANG YAYYYY");
                        System.out.println("Player 1 : " + p1.getNama() + " coba lagi ya :(");
                    }
                    else if (p2.isLose()){
                        System.out.println("PLAYER 1 : "+p1.getNama()+ " MENANG YAYYYY");
                        System.out.println("Player 2 : " + p2.getNama() + " coba lagi ya :(");
                    }

                }
                
            }
             else if (menu == 2){
                System.out.println("Permainan dimainkan oleh 2 player. Masing-masing player akan mendapatkan 6 monster yang diberikan secara acak.");
                System.out.println("Dalam fase pertarungan, kedua pemain secara bergiliran memilih antara switch monster atau move.");
                System.out.println("Untuk pilihan move, akan dieksekusi berdasarkan prioritas dan speed");
                System.out.println("Kondisi menang adalah user dengan monster setidaknya 1 monster di akhir permainan");
            }

            System.out.println(" ");
            System.out.println("MENU PERTARUNGAN");
            System.out.println("==============================");
            System.out.println("1. START GAME");
            System.out.println("2. HELP");
            System.out.println("3. EXIT");
            System.out.println("==============================");
            System.out.println("Masukkan angka: ");

            menu = aplikasi.nextInt();

        }
        System.out.println("Telah keluar dari aplikasi. Selamat Tinggal.");
        scan.close();
        aplikasi.close();
    }
}