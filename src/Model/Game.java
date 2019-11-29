package Model;

import java.util.*;
import java.io.*;

public class Game {
    public static final int NUMBER_OF_LEVELS = 3;
    private Player player;
    private ArrayList<Level> levels;
    private Score score;
    private int maxNumberOfZombies[] = {2,15,20};
    App app;

    public Game(Player player) {
        this.player = player;
        this.app = player.getApp();
        this.levels = new ArrayList<Level>(NUMBER_OF_LEVELS);
        for(int levelNo = 0; levelNo < NUMBER_OF_LEVELS; levelNo ++) {
//            levels.set(levelNo, new Level(levelNo));
            levels.add(new Level(levelNo, this,maxNumberOfZombies[levelNo]));
        }
        score = new Score(player);
    }

    public void resetLevel(int index) {
        levels.get(index).reset();
        score.resetSunPower();
    }

    public Score getScore() {
        return score;
    }

    public Level getLevel(int index) {
        return levels.get(index);
    }

    public Player getPlayer() {
        return player;
    }

    public App getApp() {
        return app;
    }
}
