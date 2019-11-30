package Controller;

import javafx.application.Platform;

import java.util.*;
import java.io.*;

public class RegularAction extends Thread {
    private static int ZOMBIE_INTERVAL = 10000;
    private static int PEA_INTERVAL = 1000;
    private static int SUNTOKEN_INTERVAL = 5000;

    private LevelController levelController;
    private long lastZombieTime;
    private long lastPeaTime;
    private long lastSuntokenTime;

    public RegularAction(LevelController levelController) {
        this.levelController = levelController;
        this.lastZombieTime = 0L;
        this.lastPeaTime = 0L;
        this.lastSuntokenTime = 0L;
    }

    public void levelCompleted() {
        levelController.getLevel().setRunning(false);
        levelController.getLevel().levelCompleted();
        Platform.runLater(() -> {
            levelController.getScene().lookup("#gameWinnerMenu").setVisible(true);
            levelController.getScene().lookup("#gameWinnerMenu").setDisable(false);
        });
    }


    @Override
    public void run() {
        while (levelController.getLevel().isRunning()) {

                while (levelController.isPause() & levelController.getLevel().isRunning()) {}

                if (levelController.getLevel().getZombies().size()==0 && levelController.getLevel().getMaxZombies()==levelController.getLevel().getCurrentNumberOfZombies()){
                    levelCompleted();
                }

                if(System.currentTimeMillis() - lastZombieTime >= ZOMBIE_INTERVAL) {
                    GenerateZombie generateZombie = new GenerateZombie(levelController);
                    Platform.runLater(generateZombie);
                    lastZombieTime = System.currentTimeMillis();
                }

                if(System.currentTimeMillis() - lastSuntokenTime >= SUNTOKEN_INTERVAL) {
                    GenerateSun generateSun = new GenerateSun(levelController);
                    Platform.runLater(generateSun);
                    lastSuntokenTime = System.currentTimeMillis();

                }

                if(System.currentTimeMillis() - lastPeaTime >= PEA_INTERVAL) {
                    ShootPeas shootPeas = new ShootPeas(levelController);
                    Platform.runLater(shootPeas);
                    lastPeaTime = System.currentTimeMillis();
                }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
