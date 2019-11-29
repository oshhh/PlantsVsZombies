package Model;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class App implements Serializable {
    private HashMap<String, Player> nameToPlayer;
    private HashMap<Player, Game> playerToGame;
    private LeaderBoard leaderBoard;

    public App() {
        nameToPlayer = new HashMap<String, Player>();
        playerToGame = new HashMap<Player, Game>();
        leaderBoard = new LeaderBoard();
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        App app = (App) obj;
        return (
                nameToPlayer.equals(app.nameToPlayer) &
                playerToGame.equals(app.playerToGame) &
                leaderBoard.equals(app.leaderBoard)
                );
    }

    @Override
    public String toString() {
        String appString = "";
        for(Map.Entry<String, Player> e : nameToPlayer.entrySet()) {
            appString += e.getKey() + ", ";
        }

        return appString;
    }

    // TODO PlayerNotFoundException
    public Player findPlayer(String name) {
        if(nameToPlayer.containsKey(name)) {
            return nameToPlayer.get(name);
        } else {
            addPlayer(name);
            return nameToPlayer.get(name);
        }
    }

    private void addPlayer(String name) {
        nameToPlayer.put(name, new Player(name, this));
        leaderBoard.addPlayer(nameToPlayer.get(name).getGame().getScore());
    }
}
