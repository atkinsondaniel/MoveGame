/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movegame;

import java.util.Random;
import java.util.Scanner;

public class MoveGame {

    public static int[][] enemies = new int[5][2];
    public static int xLoc = 10;
    public static int yLoc = 10;
    public static boolean[] rubbish = new boolean[5];
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
        moveEnemies(xLoc, yLoc);
        //moveEnemies(yLoc);

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

    public static void moveEnemies(int x, int y) {

        for (int[] trap : enemies) {
            if (x > trap[0] + 1) {
                trap[0] += 1;
            } else if (x < trap[0] + 1) {
                trap[0] -= 1;
            }
            if (y > trap[1] + 1) {
                trap[1] += 1;
            } else if (y < trap[1] + 1) {
                trap[1] -= 1;
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
        for (int[] trap : enemies) {
            if (pX == trap[0] + 1 && pY == trap[1] + 1) {
                life = false;
                System.out.println("Dead");
            }
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
        play = isDead(xLoc, yLoc);
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
        System.out.println(enemies[0][0] + "," + enemies[0][1]);
        move();

    }

    public static void checkCollisions() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = i + 1; j < enemies.length; j++) {
                if (enemies[i][0] == enemies [j][0] && enemies[i][1] == enemies [j][1]) {
                    rubbish[i] = true;
                    rubbish[j] = true;
                }
            }
        }
    }
}
