package Model;

import java.util.*;
import java.io.*;

public class LeaderBoard {
    ArrayList<Score> scores;

    public LeaderBoard() {
        scores = new ArrayList<Score>();
    }

    public void addPlayer(Score score) {
        scores.add(score);
        updateLeaderBoard();
    }

    public void updateLeaderBoard() {
        Collections.sort(scores);
    }
}
