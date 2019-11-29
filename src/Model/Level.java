package Model;

import java.util.*;
import java.io.*;

public class Level {
    private int LEVEL;

    public int NUMBER_OF_ROWS;
    private int NUMBER_OF_ZOMBIES;

    private volatile ArrayList<Zombie> zombies;
    private volatile ArrayList<Plant> plants;
    private volatile ArrayList<LawnMower> lawnMowers;
    private volatile ArrayList<Pea> peas;
    private volatile ArrayList<SunToken> sunTokens;
    private ArrayList<String> availablePlants;
    private Player player;
    private Game game;
    private volatile boolean running;
    private volatile int currentNumberOfZombies;
    private final int maxZombies;

    public Level(int levelNo, Game game, int maxZombies) {
        this.game = game;
        this.player = this.game.getPlayer();
        LEVEL = levelNo;

        zombies = new ArrayList<Zombie>();
        plants = new ArrayList<Plant>();
        lawnMowers = new ArrayList<LawnMower>();
        peas = new ArrayList<Pea>();
        sunTokens = new ArrayList<SunToken>();
        running = false;
        this.maxZombies = maxZombies;

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

    public void reset() {
        zombies = new ArrayList<Zombie>();
        plants = new ArrayList<Plant>();
        lawnMowers = new ArrayList<LawnMower>();
        running = false;
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
        synchronized (zombies) {
            zombies.add(zombie);
        }
    }
    public void removeZombie(Zombie zombie) {
        synchronized (zombies) {
            zombies.remove(zombie);
        }
    }

    public void addPlant(Plant plant) {
        synchronized (plants) {
            plants.add(plant);
        }
    }
    public void removePlant(Plant plant) {
        synchronized (plants) {
            plants.remove(plant);
        }
    }

    public void addLawnMower(LawnMower lawnMower) {
        synchronized (lawnMowers) {
            lawnMowers.add(lawnMower);
        }
    }
    public void removeLawnMower(LawnMower lawnMower) {
        synchronized (lawnMowers) {
            lawnMowers.remove(lawnMower);
        }
    }

    public void addPea(Pea pea) {
        synchronized (peas) {
            peas.add(pea);
        }
    }
    public void removePea(Pea pea) {
        synchronized (peas) {
            peas.remove(pea);
        }
    }

    public void addSunToken(SunToken sunToken) {
        synchronized (sunTokens) {
            sunTokens.add(sunToken);
        }
    }
    public void removeSunToken(SunToken sunToken) {
        synchronized (sunTokens) {
            sunTokens.remove(sunToken);
        }
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }


    public ArrayList<LawnMower> getLawnMowers() {
        return lawnMowers;
    }

    public ArrayList<Pea> getPeas() {
        return peas;
    }

    public int getCurrentNumberOfZombies() {
        return currentNumberOfZombies;
    }

    public void setCurrentNumberOfZombies(int currentNumberOfZombies) {
        this.currentNumberOfZombies = currentNumberOfZombies;
    }

    public int getMaxZombies() {
        return maxZombies;
    }
}
