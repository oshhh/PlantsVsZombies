package Controller;

import Model.Position;
import Model.Zombie;
import javafx.scene.image.ImageView;

import java.util.*;
import java.io.*;

public class GenerateZombie implements Runnable {
    LevelController levelController;
    GenerateZombie(LevelController levelController) {
        this.levelController = levelController;
    }
    @Override
    public void run() {
        // Make Zombie and add to level
        if (levelController.getLevel().getCurrentNumberOfZombies() < levelController.getLevel().getMaxZombies()) {
            Random random = new Random();
            int row = LevelController.ROW_OFFSET + random.nextInt(levelController.getLevel().NUMBER_OF_ROWS) - levelController.getLevel().NUMBER_OF_ROWS / 2;
            int column = LevelController.COLUMN_OFFSET + LevelController.NUMBER_OF_COLUMNS + random.nextInt(5);
            Position position = LevelController.getPosition(row, column);
            Zombie zombie = new Zombie(position);
            levelController.getLevel().addZombie(zombie);

            // Add to UI
            ImageView imageView = levelController.placeInAnchor(zombie);

            // Set Translation
            imageView.setTranslateY(-10);
            ZombieController zombieController = new ZombieController(levelController, zombie, imageView);
            zombieController.start();
        }
    }
}

