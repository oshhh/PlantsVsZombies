package Model;

import java.util.*;
import java.io.*;

public class Game implements Serializable {
    public static final int NUMBER_OF_LEVELS = 5;
    private ArrayList<Level> levels;
    private String playerName;
    private Score score;
    App app;

    public Game(App app, String playerName) {
        this.app = app;
        this.playerName = playerName;
        this.levels = new ArrayList<Level>(NUMBER_OF_LEVELS);
        for(int levelNo = 0; levelNo < NUMBER_OF_LEVELS; levelNo ++) {
            levels.add(new Level(levelNo, this));
        }
        score = new Score(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        Game game = (Game) obj;
        return (
                levels.equals(game.levels) &
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

    public App getApp() {
        return app;
    }

    public String getPlayerName() {
        return playerName;
    }
}
