/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movegame;

import java.util.Random;
import java.util.Scanner;

public class MoveGame {

    public static int[][] traps = new int[5][2];
    public static int xLoc = 10;
    public static int yLoc = 10;
    public static int enemyX = 25;
    public static int enemyY = 25;
    public static boolean play = true;
    public static String[][] coor = new String[30][30];
    public static int tresX;
    public static int tresY;
    public static void main(String[] args) {
        game();
    }

    public static void game() {
        makeTraps();
        while (play) {
            runGame();
        }
    }

    public static void move() {
        movePlayer();
        enemyX = moveEnemy(xLoc, enemyX);
        enemyY = moveEnemy(yLoc, enemyY);
    }

    public static void movePlayer() {
        Scanner scanner = new Scanner(System.in);
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
    }

    public static int moveEnemy(int p, int e) {
        if (p < e) {
            e -= 1;
        } else if (p > e) {
            e += 1;
        }
        return e;
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

    public static boolean isDead(int pX, int eX, int pY, int eY) {
        boolean life = true;
        if (pX == eX && pY == eY) {
            life = false;
            System.out.println("Lose");
        }
        for (int[] trap : traps) {
            if (pX == trap[0] + 1 && pY == trap[1] + 1) {
                life = false;
                System.out.println("Trapped");
            }
        }
        return life;
    }

    public static void makeTraps() {
        Random random = new Random();
        for (int[] trap : traps) {
            for (int q = 0; q < trap.length; q++) {
                trap[q] = random.nextInt(30);
            }
        }
        tresX = random.nextInt(29) + 1;
        tresY = random.nextInt(29) + 1;
    }

    public static void runGame() {
        for (int i = 0; i < coor.length; i++) {
            for (int q = 0; q < coor[i].length; q++) {

                if (q == xLoc - 1 && i == yLoc - 1) {
                    coor[q][i] = "U";
                } else if (q == enemyX - 1 && i == enemyY - 1) {
                    coor[q][i] = "E";
                } else if (q == 0 || q == 29 || i == 0 || i == 29) {
                    coor[q][i] = "X";
                } else if (q == tresX - 1 && i == tresY - 1) {
                    coor[q][i] = "T";
                }else {
                    coor[q][i] = ".";
                }
                for (int[] trap : traps) {
                    if (q == trap[0] && i == trap[1]) {
                        coor[q][i] = "*";
                    }
                }
                System.out.print(coor[q][i]);
            }
            System.out.println("");
        }
        move();
        play = isDead(xLoc, enemyX, yLoc, enemyY);

    }
}