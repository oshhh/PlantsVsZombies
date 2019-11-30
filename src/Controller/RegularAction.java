package Controller;

import javafx.application.Platform;

import java.util.*;
import java.io.*;

public class RegularAction extends Thread {
    private static int ZOMBIE_INTERVAL = 10000;
    private static int ZOMBIE_INTERVAL_WAVE = 5000;
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
        int Current_Zombie_Interval = ZOMBIE_INTERVAL;
        boolean waveOn = true;
        while (levelController.getLevel().isRunning()) {

                while (levelController.isPause() & levelController.getLevel().isRunning()) {}

                if (levelController.getLevel().getZombies().size()==0 && levelController.getLevel().getMaxZombies()==levelController.getLevel().getCurrentNumberOfZombies()){
                    levelCompleted();
                }
                if (levelController.getLevel().isFinalWaveReady() & waveOn){
                    waveOn = false;
                    Platform.runLater(() -> {
                        levelController.getScene().lookup("#finalWaveText").setVisible(true);
                    });
                    try {
                        Thread.sleep(3000);
                    }
                    catch (InterruptedException e) {}
                    Platform.runLater(() -> {
                        levelController.getScene().lookup("#finalWaveText").setVisible(false);
                    });


                    Current_Zombie_Interval = ZOMBIE_INTERVAL_WAVE;
                }
                if(System.currentTimeMillis() - lastZombieTime >= Current_Zombie_Interval) {
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
