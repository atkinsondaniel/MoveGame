/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movegame;

import java.util.Random;
import java.util.Scanner;

public class MoveGame {

    static Reg[] regEnemies; //ARRAY TO TRACK ENEMIES 5pts

    static Fas[] fasEnemies;

    static Cons[] consEnemies;
    //public static boolean[] rubbish;
    public static boolean play = true;
    public static String[][] coor = new String[30][30];
    public static Scanner scanner = new Scanner(System.in);
    public static boolean wonPrevLev = false;
    public static Booster booster;
    static Player playerguy = new Player(10, 10);

    public static void main(String[] args) {
        welcome();
        game();
    }

    public static void game() {
        regEnemies = new Reg[playerguy.nRegEnemies];
        consEnemies = new Cons[playerguy.nConsEnemies];
        fasEnemies = new Fas[playerguy.nFasEnemies];

        makeEnemies();

        while (play) {

            runGame();
        }
        if (wonPrevLev) {
            nextLevel();
        }
    }

    public static void move() {

        movePlayer();
        moveEnemies(playerguy.xLoc, playerguy.yLoc);
    }

    public static void movePlayer() {

        String nope = "Out of Bounds";
        System.out.println("Direction?");
        String resp = scanner.next().toUpperCase();
        if (resp.equals("N")) {
            if (isValid(playerguy.yLoc - 2)) {
                playerguy.yLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("S")) {
            if (isValid(playerguy.yLoc + 2)) {
                playerguy.yLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("E")) {
            if (isValid(playerguy.xLoc + 2)) {
                playerguy.xLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("W")) {
            if (isValid(playerguy.xLoc - 2)) {
                playerguy.xLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("NE")) {
            if (isValid(playerguy.xLoc + 2, playerguy.yLoc - 2)) {
                playerguy.xLoc += 1;
                playerguy.yLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("NW")) {
            if (isValid(playerguy.xLoc - 2, playerguy.yLoc - 2)) {
                playerguy.xLoc -= 1;
                playerguy.yLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("SE")) {
            if (isValid(playerguy.xLoc + 2, playerguy.yLoc + 2)) {
                playerguy.xLoc += 1;
                playerguy.yLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("SW")) {
            if (isValid(playerguy.xLoc - 2, playerguy.yLoc + 2)) {
                playerguy.xLoc -= 1;
                playerguy.yLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("T")) {
            if (playerguy.nTeleports > 0) {
                teleport(true);
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("R")) {
            teleport(false);
        }
    }

    public static void moveEnemies(int x, int y) {

        for (int i = 0; i < regEnemies.length; i++) {
            if (!regEnemies[i].rubbish) {
                if (x > regEnemies[i].x + 1) {
                     regEnemies[i].x += 1;
                } else if (x < regEnemies[i].x + 1) {
                    regEnemies[i].x -= regEnemies[i].moveVal;
                }
                if (y > regEnemies[i].y + 1) {
                    regEnemies[i].y += 1;
                } else if (y < regEnemies[i].y + 1) {
                    regEnemies[i].y -= regEnemies[i].moveVal;
                }
                isLocValid(regEnemies[i].x);
                isLocValid(regEnemies[i].y);
            }
        }
        for (int i = 0; i < consEnemies.length; i++) {
            if (!consEnemies[i].rubbish) {
                if (x > consEnemies[i].x + 1) {
                    consEnemies[i].x += consEnemies[i].moveVal;
                } else if (x < consEnemies[i].x + 1) {
                    consEnemies[i].x -= consEnemies[i].moveVal;
                }
                if (y > consEnemies[i].y + 1) {
                    consEnemies[i].y += consEnemies[i].moveVal;
                } else if (y < consEnemies[i].y + 1) {
                    consEnemies[i].y -= consEnemies[i].moveVal;
                }
                isLocValid(consEnemies[i].x);
                isLocValid(consEnemies[i].y);
            }
        }
        for (int i = 0; i < fasEnemies.length; i++) {
            if (!fasEnemies[i].rubbish) {
                if (x > fasEnemies[i].x + 1) {
                    fasEnemies[i].x += fasEnemies[i].moveVal;
                } else if (x < fasEnemies[i].x + 1) {
                    fasEnemies[i].x -= fasEnemies[i].moveVal;
                }
                if (y > fasEnemies[i].y + 1) {
                    fasEnemies[i].y += fasEnemies[i].moveVal;
                } else if (y < fasEnemies[i].y + 1) {
                    fasEnemies[i].y -= fasEnemies[i].moveVal;
                }
                isLocValid(fasEnemies[i].x);
                isLocValid(fasEnemies[i].y);
            }
        }
    }

    public static boolean isValid(int a) {
        boolean valid = true;
        if (a <= 0 || a > 30) {
            valid = false;
        }
        return valid;
    }

    public static boolean isValid(int a, int b) {
        boolean valid = true;
        if (a <= 0 || a > 30) {
            valid = false;
        }
        if (b <= 0 || b > 30) {
            valid = false;
        }
        return valid;
    }

    public static boolean isDead(int pX, int pY) {
        String robot = "      \\_/\n"
                + "     (* *)\n"
                + "    __)#(__\n"
                + "   ( )...( )(_)\n"
                + "   || |_| ||//\n"
                + ">==() | | ()/\n"
                + "    _(___)_\n"
                + "   [-]   [-]";
        String dead = "m     m                          #    \"               # \n"
                + " \"m m\"   mmm   m   m          mmm#  mmm     mmm    mmm# \n"
                + "  \"#\"   #\" \"#  #   #         #\" \"#    #    #\"  #  #\" \"# \n"
                + "   #    #   #  #   #         #   #    #    #\"\"\"\"  #   # \n"
                + "   #    \"#m#\"  \"mm\"#         \"#m##  mm#mm  \"#mm\"  \"#m## \n";
        boolean life = true;
        for (int i = 0; i < regEnemies.length; i++) {
            if (pX == regEnemies[i].x + 1 && pY == regEnemies[i].y + 1) {
                life = false;
                System.out.println(robot);
                System.out.println(dead + "You survied for " + playerguy.nRegEnemies / 2 + " levels.");
                break;
            }

        }
        for (int i = 0; i < fasEnemies.length; i++) {
            if (pX == fasEnemies[i].x + 1 && pY == fasEnemies[i].y + 1) {
                life = false;
                System.out.println(robot);
                System.out.println(dead + "You survied for " + playerguy.nRegEnemies / 2 + " levels.");
                break;
            }

        }
        for (int i = 0; i < consEnemies.length; i++) {
            if (pX == consEnemies[i].x + 1 && pY == consEnemies[i].y + 1) {
                life = false;
                System.out.println(robot);
                System.out.println(dead + "You survied for " + playerguy.nRegEnemies / 2 + " levels.");
                break;
            }

        }
        if (!life) {
            wonPrevLev = false;
        }
        return life;
    }

    public static void makeEnemies() {
        Random random = new Random();
        for (int i = 0; i < regEnemies.length; i++) {

            regEnemies[i] = new Reg(random.nextInt(25) + 2, random.nextInt(25) + 2);
        }
        for (int i = 0; i < fasEnemies.length; i++) {

            fasEnemies[i] = new Fas(random.nextInt(25) + 2, random.nextInt(25) + 2);
        }
        for (int i = 0; i < consEnemies.length; i++) {

            consEnemies[i] = new Cons(random.nextInt(25) + 2, random.nextInt(25) + 2);
        }
        booster = new Booster(random.nextInt(25) + 2, random.nextInt(25) + 2);
    }

    public static void runGame() {
        checkCollisions();
        for (int i = 0; i < coor.length; i++) {
            for (int q = 0; q < coor[i].length; q++) {

                if (q == playerguy.xLoc - 1 && i == playerguy.yLoc - 1) {
                    coor[q][i] = "U ";

                } else if (q == 0 || q == 29 || i == 0 || i == 29) { //defined level maps 5pts
                    coor[q][i] = "X ";

                } else {
                    coor[q][i] = ". ";
                }
                if (q == booster.x - 1 && i == booster.y && booster.isUp) {
                    coor[q][i] = booster.sym;
                }
                for (int r = 0; r < regEnemies.length; r++) {
                    if (q == regEnemies[r].x && i == regEnemies[r].y) {
                        coor[q][i] = regEnemies[r].symbol;
                    }

                }
                for (int r = 0; r < fasEnemies.length; r++) {
                    if (q == fasEnemies[r].x && i == fasEnemies[r].y) {
                        coor[q][i] = fasEnemies[r].symbol;
                    }

                }
                for (int r = 0; r < consEnemies.length; r++) {
                    if (q == consEnemies[r].x && i == consEnemies[r].y) {
                        coor[q][i] = consEnemies[r].symbol;
                    }

                }
                System.out.print(coor[q][i]);
            }
            System.out.println("");
        }
        System.out.println(playerguy.xLoc + "," + playerguy.yLoc);
        move();

        if (!isWin() || !isDead(playerguy.xLoc, playerguy.yLoc)) {
            play = false;

        }
    }

    public static void checkCollisions() { //turns enemies into traps 5pts
        for (int i = 0; i < regEnemies.length; i++) {
            for (int j = i + 1; j < regEnemies.length; j++) {
                if (regEnemies[i].x == regEnemies[j].x && regEnemies[i].y == regEnemies[j].y) { //comparing reg vs reg
                    regEnemies[i].rubbish = true;
                    regEnemies[i].symbol = "R ";
                    regEnemies[j].rubbish = true;
                    regEnemies[j].symbol = "R ";
                }
                for (int q = 0; q < fasEnemies.length; q++) {

                    if (regEnemies[i].x == fasEnemies[q].x && regEnemies[i].y == fasEnemies[q].y) { //comparing reg vs fast
                        regEnemies[i].rubbish = true;
                        regEnemies[i].symbol = "R ";
                        fasEnemies[q].rubbish = true;
                        fasEnemies[q].symbol = "R ";
                    }
                    if (regEnemies[j].x == fasEnemies[q].x && regEnemies[j].y == fasEnemies[q].y) { //same thing for some reason
                        regEnemies[j].rubbish = true;
                        regEnemies[j].symbol = "R ";
                        fasEnemies[q].rubbish = true;
                        fasEnemies[q].symbol = "R ";
                    }
                    for (int t = q + 1; t < fasEnemies.length; t++) {
                        if (fasEnemies[q].x == fasEnemies[t].x && fasEnemies[q].y == fasEnemies[t].y) { //comparing fast vs fast
                            fasEnemies[q].rubbish = true;
                            fasEnemies[q].symbol = "R ";
                            fasEnemies[t].rubbish = true;
                            fasEnemies[t].symbol = "R ";
                        }
                    }
                    for (int o = 0; o < consEnemies.length; o++) { //enemy skills, reviving dead enemies 5pts

                        if (regEnemies[i].x == consEnemies[o].x && regEnemies[i].y == consEnemies[o].y) { // comparing cons vs reg
                            regEnemies[i].rubbish = false;
                            regEnemies[i].symbol = "E ";
                        }
                        if (regEnemies[j].x == consEnemies[o].x && regEnemies[j].y == consEnemies[o].y) { // comparing cons vs reg
                            regEnemies[j].rubbish = false;
                            regEnemies[j].symbol = "E ";
                        }
                        if (fasEnemies[q].x == consEnemies[o].x && fasEnemies[q].y == consEnemies[o].y) { // comparing cons vs fas
                            fasEnemies[q].rubbish = false;
                            fasEnemies[q].symbol = "F ";
                        }

                    }
                }

            }

        }
        for (int i = 0; i < consEnemies.length; i++) {
            for (int j = i + 1; j < consEnemies.length; j++) { //comparing cons vs cons
                if (consEnemies[i].x == consEnemies[j].x && consEnemies[i].y == consEnemies[j].y) {
                    consEnemies[i].rubbish = true;
                    consEnemies[i].symbol = "R ";
                    consEnemies[j].rubbish = true;
                    consEnemies[j].symbol = "R ";
                }
            }
        }
        if (playerguy.xLoc == booster.x && playerguy.yLoc == booster.y && booster.isUp) {
            booster.isUp = false;
            playerguy.nTeleports += 1;
        }
    }

    public static void teleport(boolean safe) { //PLAYER CAN USE SKILLS 5pts
        Random random = new Random();
        if (safe) {
            System.out.println("X Coordinate:");
            playerguy.xLoc = scanner.nextInt();
            System.out.println("Y Coordinate:");
            playerguy.yLoc = scanner.nextInt();
            playerguy.nTeleports -= 1;
            System.out.println("You have " + playerguy.nTeleports + " remaining");
        } else {
            playerguy.xLoc = random.nextInt(25) + 2;
            playerguy.yLoc = random.nextInt(25) + 2;
        }
    }

    public static boolean isWin() {
        boolean winner = true;
        for (int i = 0; i < regEnemies.length; i++) {
            if (!regEnemies[i].rubbish) {
                winner = false;
            }
        }
        for (int i = 0; i < fasEnemies.length; i++) {
            if (!fasEnemies[i].rubbish) {
                winner = false;
            }
        }
        for (int i = 0; i < consEnemies.length; i++) {
            if (!consEnemies[i].rubbish) {
                winner = false;
            }
        }
        if (winner) {
            wonPrevLev = true;
            System.out.println("     ,     ,\n"
                    + "    (\\____/)\n"
                    + "     (_oo_)\n"
                    + "       (O)\n"
                    + "     __||__    \\)\n"
                    + "  []/______\\[] /\n"
                    + "  / \\______/ \\/\n"
                    + " /    /__\\\n"
                    + "(\\   /____\\");
            System.out.println("         m                           \"\"#   \n"
                    + "         #       mmm   m   m   mmm     #   \n"
                    + "         #      #\"  #  \"m m\"  #\"  #    #   \n"
                    + "         #      #\"\"\"\"   #m#   #\"\"\"\"    #   \n"
                    + "         #mmmmm \"#mm\"    #    \"#mm\"    \"mm ");
            System.out.println("   mmm                       \"\"#             m          \n"
                    + " m\"   \"  mmm   mmmmm  mmmm     #     mmm   mm#mm   mmm  \n"
                    + " #      #\" \"#  # # #  #\" \"#    #    #\"  #    #    #\"  # \n"
                    + " #      #   #  # # #  #   #    #    #\"\"\"\"    #    #\"\"\"\" \n"
                    + "  \"mmm\" \"#m#\"  # # #  ##m#\"    \"mm  \"#mm\"    \"mm  \"#mm\" ");
            System.out.println("");
        }
        return !winner;
    }

    public static void nextLevel() { //MULTIPLE LEVELS 10pts
        play = true;
        playerguy.nRegEnemies += 2; //GAME GETS HARDER 5pts
        playerguy.nFasEnemies += 1;
        if ((playerguy.nRegEnemies / 2) % 4 == 0) {
            playerguy.nConsEnemies += 2;
        }
        if (playerguy.nRegEnemies == 8) {
            System.out.println("Look out it's a constructor");
            System.out.println(" _(\\    |@@|\n" //ASCII ART 5pts
                + "(__/\\__ \\--/ __\n"
                + "   \\___|----|  |   __\n"
                + "       \\ }{ /\\ )_ / _\\\n"
                + "       /\\__/\\ \\__O (__\n"
                + "      (--/\\--)    \\__/\n"
                + "      _)(  )(_\n"
                + "     `---''---`");
            System.out.println("If they collide when broken robots \n they will be rebuilt! \n The only way to kill a "
                    + "constructor is with a constructor.");
            
        }
        System.out.println("Welcome to level " + playerguy.nRegEnemies / 2);
        game();
    }

    private static void welcome() {
        

        System.out.println("m     m        \"\"#                               \n"
                + "#  #  #  mmm     #     mmm    mmm   mmmmm   mmm  \n"
                + "\" #\"# # #\"  #    #    #\"  \"  #\" \"#  # # #  #\"  # \n"
                + " ## ##\" #\"\"\"\"    #    #      #   #  # # #  #\"\"\"\" \n"
                + " #   #  \"#mm\"    \"mm  \"#mm\"  \"#m#\"  # # #  \"#mm\" ");
        System.out.println("N,S,E,W and their combinations move you.");
        System.out.println("Make the enemies crash into each other to win.");
        System.out.println("\"T\" will allow you to teleport to a certain location.");
        System.out.println("\"R\" will teleport you to a random location.");
    }

    public static int isLocValid(int x) { //walls block enemy movement 5pts
        if (x == 0) {
            x += 1;
        } else if (x == 30) {
            x -= 1;
        }
        return x;
    }
}

class Player { //PLAYER CONVERTED TO OBJECT 10pts

    int xLoc;
    int yLoc;
    int nTeleports;
    static int nRegEnemies = 2;
    static int nFasEnemies = 0;
    static int nConsEnemies = 0;

    public Player(int x, int y) {
        this.xLoc = x;
        this.yLoc = y;
        this.nTeleports = 5;
    }
}

class Enemy { //ENEMY CLASS 5pts

    int x;
    int y;
    boolean rubbish;
    int moveVal;
    String symbol;

    public Enemy(int xLoc, int yLoc, boolean rubbishVal, int moveValcool, String sym) {
        this.x = xLoc;
        this.y = yLoc;
        this.rubbish = rubbishVal;
        this.moveVal = moveValcool;
        this.symbol = sym;
    }
}

class Reg extends Enemy { //MULTIPLE ENEMY CLASSES 5pts

    public Reg(int xLoc, int yLoc) {
        super(xLoc, yLoc, false, 1, "E ");
    }
}

class Fas extends Enemy { //ENEMY SUBCLASSES 7pts

    public Fas(int xLoc, int yLoc) {
        super(xLoc, yLoc, false, 2, "F ");
    }
}

class Cons extends Enemy {

    public Cons(int xLoc, int yLoc) {
        super(xLoc, yLoc, false, 1, "C ");
    }
}
class Booster { //loot classes 5pts
    int x;
    int y;
    boolean isUp;
    String sym = "T ";
    public Booster(int xLoc, int yLoc) {
        this.x = xLoc;
        this.y = yLoc;
        this.isUp = true;
    }
}
