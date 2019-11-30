package Controller;

import Model.*;

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
                    Position zombiePosition = zombie.getPosition();
                    if(!zombie.isAttacking()) {
                        synchronized (levelController.getLevel().getPlants()) {
                            for (Plant plant: levelController.getLevel().getPlants()){
                                Position plantPosition = plant.getPosition();
                                if (Math.abs(zombiePosition.getX()-plantPosition.getX()) <= LevelController.COLLISION_RADIUS &&
                                        Math.abs(zombiePosition.getY()-plantPosition.getY()) <= LevelController.COLLISION_RADIUS){
                                    levelController.handlePlantZombieCollision(plant, zombie);
                                }
                            }
                        }
                    }
                    synchronized (levelController.getLevel().getPeas()) {
                        for (Pea pea: levelController.getLevel().getPeas()){
                            Position peaPosition = pea.getPosition();
                            if (Math.abs(zombiePosition.getX() - peaPosition.getX()) <= LevelController.COLLISION_RADIUS
                                    && Math.abs(zombiePosition.getY() - peaPosition.getY()) <= LevelController.COLLISION_RADIUS){
                                levelController.handlePeaZombieCollision(pea, zombie);
                            }
                        }
                    }
                    synchronized (levelController.getLevel().getLawnMowers()) {
                        for (LawnMower lawnMower: levelController.getLevel().getLawnMowers()){
                            Position lawnMowerPosition = lawnMower.getPosition();
                            if (Math.abs(zombiePosition.getX()-lawnMowerPosition.getX()) <= LevelController.COLLISION_RADIUS
                                    && Math.abs(zombiePosition.getY()-lawnMowerPosition.getY()) <= LevelController.COLLISION_RADIUS){
                                levelController.handleLawnMowerZombieCollision(lawnMower, zombie);
                            }
                        }
                    }
                }
            }
            try {
                Thread.sleep(LevelController.ANIMATION_TIMEGAP/10);
            } catch (InterruptedException e) {}
        }
    }
}

