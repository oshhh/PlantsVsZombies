package Controller;

import Model.Plant;
import Model.Zombie;

import java.util.*;
import java.io.*;

public class ZombiePlantFight extends Thread {
    private Plant plant;
    private Zombie zombie;

    public ZombiePlantFight(Plant plant, Zombie zombie) {
        this.plant = plant;
        this.zombie = zombie;
    }

    @Override
    public void run() {
        zombie.setAttacking(true);
        zombie.setMoving(false);
        while (plant.isAlive() & zombie.isAlive()) {
            plant.changeHealth(-1*zombie.attack());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        if(zombie.isAlive()) {
            zombie.setMoving(true);
            zombie.setAttacking(false);
        }
    }
}

