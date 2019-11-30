package Model;

import java.util.*;
import java.io.*;

public class Score implements Comparable<Score>, Serializable {
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

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        Score score = (Score) obj;
        return (
                player.equals(score.player) &
                currentLevel == score.currentLevel &
                sunPower == score.sunPower &
                coins == score.coins
        );

    }

    public void levelWon(int level) {
        System.out.println(level);
        coins += (50) * (level + 1);
        resetSunPower();
        if(level == currentLevel) {
            levelUp();
        }
    }

    public void levelUp() {
        currentLevel ++;
    }

    public void addSunPower() {
        this.sunPower += SUN_POWER;
    }

    public void resetSunPower() {
        sunPower = 0;
    }
    public void reduceSunPower(int reducePower) {
        this.sunPower = Math.max(0, this.sunPower-reducePower);
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
    public int getCurrentLevel() {
        return currentLevel;
    }
    public int getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return  "Player Name: " + player.getName() + " | " +
                "Current Level: " + currentLevel + " | " +
                "Coins: " + coins + " | ";
    }
}
