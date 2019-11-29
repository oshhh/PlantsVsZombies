package Controller;

import javafx.application.Platform;

import java.util.*;
import java.io.*;

public class RegularAction extends Thread {
    private LevelController levelController;

    public RegularAction(LevelController levelController) {
        this.levelController = levelController;
    }

    @Override
    public void run() {
        while (levelController.getLevel().isRunning()) {
            try {

                // We can't change UI in any other thread except JavaFX thread
                // We need to add all UI changes to platform.runLater()
                // which will run those UI changing threads in the JavaFX thread

                // Run thread that generates zombie
                while (levelController.isPause() & levelController.getLevel().isRunning()) {}

                if (levelController.getLevel().getZombies().size()==0 && levelController.getLevel().getMaxZombies()==levelController.getLevel().getCurrentNumberOfZombies()){
                    levelController.getLevel().setRunning(false);
                    Platform.runLater(() -> {
                        levelController.getScene().lookup("#gameWinnerMenu").setVisible(true);
                        levelController.getScene().lookup("#gameWinnerMenu").setDisable(false);
                    });
                }

                GenerateZombie generateZombie = new GenerateZombie(levelController);
                Platform.runLater(generateZombie);

                Thread.sleep(3000);

                while (levelController.isPause() & levelController.getLevel().isRunning()) {}

                // Run thread that generates sun
                GenerateSun generateSun = new GenerateSun(levelController);
                Platform.runLater(generateSun);


                Thread.sleep(2000);

                while (levelController.isPause() & levelController.getLevel().isRunning()) {}

                ShootPeas shootPeas = new ShootPeas(levelController);
                Platform.runLater(shootPeas);

            } catch (InterruptedException e) {}
        }
    }
}
