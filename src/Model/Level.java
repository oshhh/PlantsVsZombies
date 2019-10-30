package Model;

import java.util.*;
import java.io.*;

public class Level {
    private int LEVEL;

    private int NUMBER_OF_ZOMBIES;

    ArrayList<Zombie> zombies;
    ArrayList<String> availablePlants;

    public Level(int levelNo) {
        LEVEL = levelNo;
        switch (LEVEL) {
            case 0:
                NUMBER_OF_ZOMBIES = 5;
                availablePlants = new ArrayList<String>();
                availablePlants.add("PeaShooter");
                availablePlants.add("SunFlower");
                break;
            case 1:
                NUMBER_OF_ZOMBIES = 7;
                availablePlants = new ArrayList<String>();
                availablePlants.add("PeaShooter");
                availablePlants.add("SunFlower");
                availablePlants.add("WallNut");
                break;
            case 2:
                NUMBER_OF_ZOMBIES = 10;
                availablePlants = new ArrayList<String>();
                availablePlants.add("PeaShooter");
                availablePlants.add("SunFlower");
                availablePlants.add("WallNut");
                availablePlants.add("CherryBomb");
                break;

        }
    }
}
