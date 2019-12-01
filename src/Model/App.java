package Model;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class App implements Serializable {
    private HashMap<String, Game> playerToGame;
    private LeaderBoard leaderBoard;

    public App() {
        playerToGame = new HashMap<String, Game>();
        leaderBoard = new LeaderBoard();
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        App app = (App) obj;
        return (
                playerToGame.equals(app.playerToGame) &
                leaderBoard.equals(app.leaderBoard)
                );
    }



    public void addPlayer(String name) {
        Game game = new Game(this, name);
        playerToGame.put(name, game);
        leaderBoard.addPlayer(game.getScore());
    }

    public Game getGame(String name){
        return playerToGame.get(name);
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }
}
