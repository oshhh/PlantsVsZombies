package Model;

import java.util.*;
import java.io.*;

public class Game {
    public static final int NUMBER_OF_LEVELS = 5;

    private Player player;
    private ArrayList<Level> levels;
    private Score score;

    public Game(Player player) {
        this.player = player;
        this.levels = new ArrayList<Level>(NUMBER_OF_LEVELS);
        for(int levelNo = 0; levelNo < NUMBER_OF_LEVELS; levelNo ++) {
            levels.set(levelNo, new Level(levelNo));
        }
        score = new Score(player);
    }

    public Score getScore() {
        return score;
    }
}
