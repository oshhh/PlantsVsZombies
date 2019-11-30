package Model;

import javafx.scene.Scene;

import java.util.*;
import java.io.*;

public class LeaderBoard implements Serializable {
    private ArrayList<Score> scores;

    public LeaderBoard() {
        scores = new ArrayList<Score>();
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        LeaderBoard leaderBoard = (LeaderBoard) obj;
        return (
                scores.equals(leaderBoard.scores)
        );
    }

    @Override
    public String toString() {
        String leaderboardString = "";
        for(Score score: scores) {
            leaderboardString += score + "\n";
        }
        return leaderboardString;
    }

    public void addPlayer(Score score) {
        scores.add(score);
        updateLeaderBoard();
    }

    public void updateLeaderBoard() {
        Collections.sort(scores);
    }

    public ArrayList<Score> getScores() {
        return scores;
    }
}
