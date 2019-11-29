package Model;

import java.util.*;
import java.io.*;

public class Score implements Comparable<Score> {
    private Player player;
    private static final int SUN_POWER = 50;
    private int currentLevel;
    private volatile int sunPower;
    private volatile int coins;

    public Score(Player player) {
        this.player = player;
        this.sunPower = 0;
        this.coins = 0;
        this.currentLevel = 0;
    }

    public void levelUp() {
        currentLevel ++;
        resetSunPower();
    }

    public void addSunPower() {
        this.sunPower += SUN_POWER;
    }

    public void resetSunPower() {
        sunPower = 0;
    }

    @Override
    public int compareTo(Score o) {
        if(this.currentLevel > o.currentLevel) {
            return -1;
        }
        if(this.currentLevel == o.currentLevel & this.coins > o.coins) {
            return -1;
        }

        if(this.currentLevel == o.currentLevel & this.coins == o.coins) {
            return 0;
        }

        return 1;
    }

    public int getSunPower() {
        return sunPower;
    }

    @Override
    public String toString() {
        return  "Player Name: " + player.getName() + "\n" +
                "Current Level: " + currentLevel + "\n" +
                "Coins: " + coins + "\n";
    }
}
