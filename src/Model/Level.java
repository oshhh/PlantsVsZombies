package Model;

import Controller.LevelController;

import java.util.*;
import java.io.*;

public class Level implements Serializable {
    private int LEVEL;

    public int NUMBER_OF_ROWS;
    private int NUMBER_OF_ZOMBIES;

    private volatile ArrayList<Zombie> zombies;
    private volatile ArrayList<Plant> plants;
    private volatile ArrayList<LawnMower> lawnMowers;
    private volatile ArrayList<Pea> peas;
    private volatile ArrayList<SunToken> sunTokens;
    private HashMap<String, Long> availablePlants;
    private Player player;
    private Game game;
    private volatile boolean running;
    private volatile int currentNumberOfZombies;
    private boolean active;
    private HashMap<String,Integer> plantPrices;

    public Level(int levelNo, Game game, boolean active) {
        this.game = game;
        this.player = this.game.getPlayer();
        this.active = active;
        LEVEL = levelNo;

        zombies = new ArrayList<Zombie>();
        plants = new ArrayList<Plant>();
        lawnMowers = new ArrayList<LawnMower>();
        peas = new ArrayList<Pea>();
        sunTokens = new ArrayList<SunToken>();
        running = false;
        plantPrices = new HashMap<String, Integer>();
        plantPrices.put("PeaShooter",100);
        plantPrices.put("SunFlower",50);
        plantPrices.put("WallNut",50);
        plantPrices.put("CherryBomb",150);

        switch (LEVEL) {
            case 0:
                NUMBER_OF_ZOMBIES = 5;
                NUMBER_OF_ROWS = 1;
                availablePlants = new HashMap<String, Long>();
                availablePlants.put("PeaShooter", 0L);
                availablePlants.put("SunFlower", 0L);
                break;
            case 1:
                NUMBER_OF_ROWS = 3;
                NUMBER_OF_ZOMBIES = 7;
                availablePlants = new HashMap<String, Long>();
                availablePlants.put("PeaShooter", 0L);
                availablePlants.put("SunFlower", 0L);
                availablePlants.put("WallNut", 0L);
                break;
            case 2:
                NUMBER_OF_ROWS = 5;
                NUMBER_OF_ZOMBIES = 10;
                availablePlants = new HashMap<String, Long>();
                availablePlants.put("PeaShooter", 0L);
                availablePlants.put("SunFlower", 0L);
                availablePlants.put("WallNut", 0L);
                availablePlants.put("CherryBomb", 0L);
                break;

        }

        for(int i = 0; i < NUMBER_OF_ROWS; i ++) {
            LawnMower lawnMower = new LawnMower(LevelController.getPosition(i + LevelController.ROW_OFFSET - NUMBER_OF_ROWS/2, LevelController.COLUMN_OFFSET - 1));
            addLawnMower(lawnMower);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        Level level = (Level) obj;
        return (
                zombies.equals(level.zombies) &
                plants.equals(level.plants) &
                lawnMowers.equals(level.lawnMowers) &
                peas.equals(level.peas) &
                sunTokens.equals(level.peas) &
                availablePlants.equals(level.availablePlants) &
                player.equals(level.player) &
                game.equals(level.game) &
                running == level.running &
                currentNumberOfZombies == level.currentNumberOfZombies
                );
    }

    @Override
    public String toString() {
        return "Player: " + player.getName() + " | level no: " + getLEVEL();
    }

    public HashMap<String, Long> getAvailablePlants() {
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
            currentNumberOfZombies ++;
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

    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMaxZombies() {
        return NUMBER_OF_ZOMBIES;
    }

    public ArrayList<SunToken> getSunTokens() {
        return sunTokens;
    }

    public void levelWon() {
        this.setRunning(false);
        game.levelUp();
        System.out.println(game);
        if(LEVEL != 2) {
            this.game.getLevel(LEVEL + 1).setActive(true);
        }
        this.game.resetLevel(LEVEL);
    }

    public void levelLost() {
        this.setRunning(false);
    }

    public void useSunTokens(int reducePower) {
        game.getScore().reduceSunPower(reducePower);
    }

    public HashMap<String, Integer> getPlantPrices() {
        return plantPrices;
    }
}
