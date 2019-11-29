package Model;

import java.util.*;
import java.io.*;

public class Player implements Serializable {
    private String name;
    private Game game;
    private App app;

    Player(String name, App app) {
        this.name = name;
        this.app = app;
        this.game = new Game(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        Player player = (Player) obj;
        return (
                name.equals(player.name) &
                game.equals(player.game) &
                app.equals(player.app)
        );

    }

    @Override
    public String toString() {
        return getName();
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
