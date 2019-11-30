package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.scene.Node;

import java.util.*;
import java.io.*;

public class DetectCollision extends Thread{
    private LevelController levelController;

    public void handlePeaZombieCollision(Pea pea, Zombie zombie) {
        if(!pea.isAlive() | !zombie.isAlive())  return;
        pea.setAlive(false);
        zombie.changeHealth(-1*Pea.PEA_ATTACK_POWER);
    }
    public void handlePlantZombieCollision(Plant plant, Zombie zombie) {
        if(!plant.isAlive() | !zombie.isAlive())  return;
        ZombiePlantFight zombiePlantFight = new ZombiePlantFight(plant, zombie);
        zombiePlantFight.start();
    }
    public void handleLawnMowerZombieCollision(LawnMower lawnMower, Zombie zombie) {
        if(!lawnMower.isAlive() | !zombie.isAlive())  return;
        if(!lawnMower.isMowing()) {
            lawnMower.setMowing(true);
        }
        zombie.setAlive(false);
    }
    public void gameOver() {
        levelController.getLevel().setRunning(false);
        Game game = levelController.getLevel().getGame();
        game.resetLevel(levelController.getLevel().getLEVEL());
        Platform.runLater(() -> {
            Node node = levelController.getScene().lookup("#gameOverMenu");
            node.setDisable(false);
            node.setVisible(true);
        });
    }

    DetectCollision(LevelController levelController) {
        this.levelController = levelController;
    }

    @Override
    public void run(){
        while (levelController.getLevel().isRunning()){
            while (levelController.isPause() & levelController.getLevel().isRunning()) {}
            synchronized (levelController.getLevel().getZombies()) {
                for (Zombie zombie: levelController.getLevel().getZombies()){
                    if(!zombie.isAttacking()) {
                        synchronized (levelController.getLevel().getPlants()) {
                            for (Plant plant: levelController.getLevel().getPlants()){
                                if (Math.abs(zombie.getPosition().getX()-plant.getPosition().getX()) <= LevelController.COLLISION_RADIUS &&
                                        Math.abs(zombie.getPosition().getY()-plant.getPosition().getY()) <= LevelController.COLLISION_RADIUS){
                                    handlePlantZombieCollision(plant, zombie);
                                }
                            }
                        }
                    }
                    synchronized (levelController.getLevel().getPeas()) {
                        for (Pea pea: levelController.getLevel().getPeas()){
                            if (Math.abs(zombie.getPosition().getX() - pea.getPosition().getX()) <= LevelController.COLLISION_RADIUS
                                    && Math.abs(zombie.getPosition().getY() - pea.getPosition().getY()) <= LevelController.COLLISION_RADIUS){
                                handlePeaZombieCollision(pea, zombie);
                            }
                        }
                    }
                    synchronized (levelController.getLevel().getLawnMowers()) {
                        for (LawnMower lawnMower: levelController.getLevel().getLawnMowers()){
                            if (Math.abs(zombie.getPosition().getX()-lawnMower.getPosition().getX()) <= LevelController.COLLISION_RADIUS
                                    && Math.abs(zombie.getPosition().getY()-lawnMower.getPosition().getY()) <= LevelController.COLLISION_RADIUS){
                                handleLawnMowerZombieCollision(lawnMower, zombie);
                            }
                        }
                    }
                    if (zombie.getPosition().getX()<= LevelController.getPosition(0,-1).getX()){
                        gameOver();

                    }
                }
            }
            try {
                Thread.sleep(LevelController.ANIMATION_TIMEGAP);
            } catch (InterruptedException e) {}
        }
    }
}

