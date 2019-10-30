package Model;

import java.security.PublicKey;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class App {
    private HashMap<String, Player> nameToPlayer;
    private HashMap<Player, Game> playerToGame;
    private LeaderBoard leaderBoard;

    public App() {
        nameToPlayer = new HashMap<String, Player>();
        playerToGame = new HashMap<Player, Game>();
        leaderBoard = new LeaderBoard();
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
        nameToPlayer.put(name, new Player(name));
        leaderBoard.addPlayer(nameToPlayer.get(name).getGame().getScore());
    }

    // TODO serialize game
    public void loadGame() {}

    // TODO deserialise game
    public void saveGame() {}

    // TODO equals
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
