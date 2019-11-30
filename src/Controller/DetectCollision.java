package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.scene.Node;

import java.util.*;
import java.io.*;

public class DetectCollision extends Thread{
    private LevelController levelController;

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
                                    levelController.handlePlantZombieCollision(plant, zombie);
                                }
                            }
                        }
                    }
                    synchronized (levelController.getLevel().getPeas()) {
                        for (Pea pea: levelController.getLevel().getPeas()){
                            if (Math.abs(zombie.getPosition().getX() - pea.getPosition().getX()) <= LevelController.COLLISION_RADIUS
                                    && Math.abs(zombie.getPosition().getY() - pea.getPosition().getY()) <= LevelController.COLLISION_RADIUS){
                                levelController.handlePeaZombieCollision(pea, zombie);
                            }
                        }
                    }
                    synchronized (levelController.getLevel().getLawnMowers()) {
                        for (LawnMower lawnMower: levelController.getLevel().getLawnMowers()){
                            if (Math.abs(zombie.getPosition().getX()-lawnMower.getPosition().getX()) <= LevelController.COLLISION_RADIUS
                                    && Math.abs(zombie.getPosition().getY()-lawnMower.getPosition().getY()) <= LevelController.COLLISION_RADIUS){
                                levelController.handleLawnMowerZombieCollision(lawnMower, zombie);
                            }
                        }
                    }
                    if (zombie.getPosition().getX()<= LevelController.getPosition(0,-1).getX()){
                        levelController.loseGame();

                    }
                }
            }
            try {
                Thread.sleep(LevelController.ANIMATION_TIMEGAP);
            } catch (InterruptedException e) {}
        }
    }
}

