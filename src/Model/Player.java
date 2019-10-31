package Model;

import java.util.*;
import java.io.*;

public class Player {
    private String name;
    private Game game;
    private App app;

    Player(String name, App app) {
        this.name = name;
        this.app = app;
        this.game = new Game(this);
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    public App getApp() {
        return app;
    }
}
