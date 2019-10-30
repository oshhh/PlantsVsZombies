package Model;

import java.util.*;
import java.io.*;

public class Player {
    private String name;
    private Game game;

    Player(String name) {
        this.name = name;
        this.game = new Game(this);
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }
}
