/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movegame;

import java.util.Random;
import java.util.Scanner;

public class MoveGame {

    public static int nEnemies = 2;
    static Enemy[] enemies;

    //public static boolean[] rubbish;
    public static boolean play = true;
    public static String[][] coor = new String[30][30];
    public static Scanner scanner = new Scanner(System.in);
    public static boolean wonPrevLev = false;

    public static int moveVal = 1;
    static Player playerguy = new Player(10, 10);

    public static void main(String[] args) {
        welcome();
        game();
    }

    public static void game() {
        enemies = new Enemy[nEnemies];        
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

        for (int i = 0; i < enemies.length; i++) {
            if (!enemies[i].rubbish) {
                if (x > enemies[i].x + 1) {
                    enemies[i].x += moveVal;
                } else if (x < enemies[i].x + 1) {
                    enemies[i].x -= enemies[i].moveVal;
                }
                if (y > enemies[i].y + 1) {
                    enemies[i].y += moveVal;
                } else if (y < enemies[i].y + 1) {
                    enemies[i].y -= enemies[i].moveVal;
                }
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
        boolean life = true;
        for (int i = 0; i < enemies.length; i++) {
            if (pX == enemies[i].x + 1 && pY == enemies[i].y + 1) {
                life = false;
                System.out.println("m     m                          #    \"               # \n"
                        + " \"m m\"   mmm   m   m          mmm#  mmm     mmm    mmm# \n"
                        + "  \"#\"   #\" \"#  #   #         #\" \"#    #    #\"  #  #\" \"# \n"
                        + "   #    #   #  #   #         #   #    #    #\"\"\"\"  #   # \n"
                        + "   #    \"#m#\"  \"mm\"#         \"#m##  mm#mm  \"#mm\"  \"#m## \n"
                        + "You survied for " + nEnemies / 2 + " levels.");
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
        for (int i = 0; i < enemies.length; i++) {

            enemies[i] = new Enemy(random.nextInt(25) + 2,random.nextInt(25) + 2,false,moveVal);
        }
    }

    public static void runGame() {

        checkCollisions();
        for (int i = 0; i < coor.length; i++) {
            for (int q = 0; q < coor[i].length; q++) {

                if (q == playerguy.xLoc - 1 && i == playerguy.yLoc - 1) {
                    coor[q][i] = "U ";
                } else if (q == 0 || q == 29 || i == 0 || i == 29) {
                    coor[q][i] = "X ";
                } else {
                    coor[q][i] = ". ";
                }
                for (int r = 0; r < enemies.length; r++) {
                    if (q == enemies[r].x && i == enemies[r].y && enemies[r].rubbish == false) {
                        coor[q][i] = "E ";
                    } else if (q == enemies[r].x && i == enemies[r].y && enemies[r].rubbish == true) {
                        coor[q][i] = "R ";
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

    public static void checkCollisions() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = i + 1; j < enemies.length; j++) {
                if (enemies[i].x == enemies[j].x && enemies[i].y == enemies[j].y) {
                    enemies[i].rubbish = true;
                    enemies[j].rubbish = true;
                }
            }
        }

    }

    public static void teleport(boolean safe) {
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
        for (int i = 0; i < enemies.length; i++) {
            if (!enemies[i].rubbish) {
                winner = false;
            }
        }
        if (winner) {
            wonPrevLev = true;
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

    public static void nextLevel() {
        if (nEnemies % 10 == 0) {
            moveVal += 1;
        }
        play = true;
        nEnemies += 2;
        System.out.println("Welcome to level " + nEnemies / 2);
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
}

class Player {

    int xLoc;
    int yLoc;
    int nTeleports;

    public Player(int x, int y) {
        this.xLoc = x;
        this.yLoc = y;
        this.nTeleports = 5;
    }
}

class Enemy {
    int x;
    int y; 
    boolean rubbish;
    int moveVal;
    public Enemy(int xLoc, int yLoc, boolean rubbishVal, int moveValcool) {
        this.x = xLoc;
        this.y = yLoc;
        this.rubbish = rubbishVal;
        this.moveVal = moveValcool;
    }
}
