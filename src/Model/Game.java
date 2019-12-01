package Model;

import java.util.*;
import java.io.*;

public class Game implements Serializable {
    public static final int NUMBER_OF_LEVELS = 5;
    private Player player;
    private ArrayList<Level> levels;
    private Score score;
    App app;

    public Game(Player player) {
        this.player = player;
        this.app = player.getApp();
        this.levels = new ArrayList<Level>(NUMBER_OF_LEVELS);
        for(int levelNo = 0; levelNo < NUMBER_OF_LEVELS; levelNo ++) {
            levels.add(new Level(levelNo, this));
        }
        score = new Score(player);
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        Game game = (Game) obj;
        return (
                player.equals(game.player) &
                levels.equals(game.levels) &
                score.equals(game.score) &
                app.equals(game.app)
                );
    }

    public void resetLevel(int index) {
        levels.set(index, new Level(index, this));
        score.resetSunPower();
    }

    public void levelUp() {
        score.levelUp();
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

    @Override
    public String toString() {
        return "Player:" + player.getName() + " | Score: " + score ;
    }
}
