/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movegame;

import java.util.Random;
import java.util.Scanner;

public class MoveGame {

    public static int nEnemies = 0;
    public static int[][] enemies;
    public static int xLoc = 10;
    public static int yLoc = 10;
    public static boolean[] rubbish;
    public static boolean play = true;
    public static String[][] coor = new String[30][30];
    public static int tresX;
    public static int tresY;
    public static Scanner scanner = new Scanner(System.in);
    public static boolean wonPrevLev = true;

    public static void main(String[] args) {
        welcome();
        game();
    }

    public static void game() {
        
        enemies = new int[nEnemies][2];
        rubbish = new boolean[nEnemies];
        makeTraps();
        while (play) {

            runGame();
        }
        if (wonPrevLev) {
            nextLevel();
        }
    }

    public static void move() {

        movePlayer();
        moveEnemies(xLoc, yLoc);
    }

    public static void movePlayer() {

        String nope = "Out of Bounds";
        System.out.println("Direction?");
        String resp = scanner.next().toUpperCase();
        if (resp.equals("N")) {
            if (isValid(yLoc - 2)) {
                yLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("S")) {
            if (isValid(yLoc + 2)) {
                yLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("E")) {
            if (isValid(xLoc + 2)) {
                xLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("W")) {
            if (isValid(xLoc - 2)) {
                xLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("NE")) {
            if (isValid(xLoc + 2, yLoc - 2)) {
                xLoc += 1;
                yLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("NW")) {
            if (isValid(xLoc - 2, yLoc - 2)) {
                xLoc -= 1;
                yLoc -= 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("SE")) {
            if (isValid(xLoc + 2, yLoc + 2)) {
                xLoc += 1;
                yLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("SW")) {
            if (isValid(xLoc - 2, yLoc + 2)) {
                xLoc -= 1;
                yLoc += 1;
            } else {
                System.out.println(nope);
            }
        }
        if (resp.equals("T")) {
            teleport(true);
        }
        if (resp.equals("U")) {
            teleport(false);
        }
    }

    public static void moveEnemies(int x, int y) {

        for (int i = 0; i < enemies.length; i++) {
            if (!rubbish[i]) {
                if (x > enemies[i][0] + 1) {
                    enemies[i][0] += 1;
                } else if (x < enemies[i][0] + 1) {
                    enemies[i][0] -= 1;
                }
                if (y > enemies[i][1] + 1) {
                    enemies[i][1] += 1;
                } else if (y < enemies[i][1] + 1) {
                    enemies[i][1] -= 1;
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
            if (pX == enemies[i][0] + 1 && pY == enemies[i][1] + 1) {
                life = false;
                System.out.println("Dead");
                break;
            }

        }
        if (!life) {
            wonPrevLev = false;
        }
        return life;
    }

    public static void makeTraps() {
        Random random = new Random();
        for (int[] trap : enemies) {
            for (int q = 0; q < trap.length; q++) {
                trap[q] = random.nextInt(30);
                rubbish[q] = false;
            }
        }
        tresX = random.nextInt(29) + 1;
        tresY = random.nextInt(29) + 1;
    }

    public static void runGame() {

        checkCollisions();
        for (int i = 0; i < coor.length; i++) {
            for (int q = 0; q < coor[i].length; q++) {

                if (q == xLoc - 1 && i == yLoc - 1) {
                    coor[q][i] = "U ";
                } else if (q == 0 || q == 29 || i == 0 || i == 29) {
                    coor[q][i] = "X ";
                } else {
                    coor[q][i] = ". ";
                }
                for (int r = 0; r < enemies.length; r++) {
                    if (q == enemies[r][0] && i == enemies[r][1] && rubbish[r] == false) {
                        coor[q][i] = "E ";
                    } else if (q == enemies[r][0] && i == enemies[r][1] && rubbish[r] == true) {
                        coor[q][i] = "R ";
                    }

                }
                System.out.print(coor[q][i]);
            }
            System.out.println("");
        }
        System.out.println(xLoc + "," + yLoc);
        move();

        if (!isWin() || !isDead(xLoc, yLoc)) {
            play = false;

        }
    }

    public static void checkCollisions() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = i + 1; j < enemies.length; j++) {
                if (enemies[i][0] == enemies[j][0] && enemies[i][1] == enemies[j][1]) {
                    rubbish[i] = true;
                    rubbish[j] = true;
                }
            }
        }

    }

    public static void teleport(boolean safe) {
        Random random = new Random();
        if (safe) {
        System.out.println("X Coordinate:");
        xLoc = scanner.nextInt();
        System.out.println("Y Coordinate:");
        yLoc = scanner.nextInt();
        } else {
            xLoc = random.nextInt(25 + 2);
            yLoc = random.nextInt(25 + 2);
        }
    }

    public static boolean isWin() {
        boolean winner = true;
        for (int i = 0; i < rubbish.length; i++) {
            if (!rubbish[i]) {
                winner = false;
            }
        }
        if (winner) {
            wonPrevLev = true;
        }
        return !winner;
    }

    public static void nextLevel() {
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
