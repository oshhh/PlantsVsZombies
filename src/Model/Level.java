package Model;

import java.util.*;
import java.io.*;

public class Level {
    private int LEVEL;

    public int NUMBER_OF_ROWS;
    private int NUMBER_OF_ZOMBIES;

    ArrayList<Zombie> zombies;
    ArrayList<Plant> plants;
    ArrayList<String> availablePlants;
    Player player;
    Game game;

    public Level(int levelNo, Game game) {
        this.game = game;
        this.player = this.game.getPlayer();
        LEVEL = levelNo;

        zombies = new ArrayList<Zombie>();
        plants = new ArrayList<Plant>();

        switch (LEVEL) {
            case 0:
                NUMBER_OF_ZOMBIES = 5;
                NUMBER_OF_ROWS = 1;
                availablePlants = new ArrayList<String>();
                availablePlants.add("PeaShooter");
                availablePlants.add("SunFlower");
                break;
            case 1:
                NUMBER_OF_ROWS = 3;
                NUMBER_OF_ZOMBIES = 7;
                availablePlants = new ArrayList<String>();
                availablePlants.add("PeaShooter");
                availablePlants.add("SunFlower");
                availablePlants.add("WallNut");
                break;
            case 2:
                NUMBER_OF_ROWS = 5;
                NUMBER_OF_ZOMBIES = 10;
                availablePlants = new ArrayList<String>();
                availablePlants.add("PeaShooter");
                availablePlants.add("SunFlower");
                availablePlants.add("WallNut");
                availablePlants.add("CherryBomb");
                break;

        }
    }

    public ArrayList<String> getAvailablePlants() {
        return availablePlants;
    }

    public int getLEVEL() {
        return LEVEL;
    }

    public Player getPlayer() {
        return player;
    }

    public void collectSun() {
        game.getScore().addSunPower();
    }

    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }
}
