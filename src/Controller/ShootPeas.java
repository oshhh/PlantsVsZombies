package Controller;

import Model.Pea;
import Model.PeaShooter;
import Model.Plant;
import Model.Position;
import javafx.scene.image.ImageView;

import java.util.*;
import java.io.*;

public class ShootPeas implements Runnable{
    private LevelController levelController;

    public ShootPeas(LevelController levelController) {
        this.levelController = levelController;
    }

    @Override
    public void run(){
        for(Plant plant: levelController.getLevel().getPlants()) {
            if(!plant.getClass().equals(PeaShooter.class))  continue;

            Position position = new Position(plant.getPosition().getX(), plant.getPosition().getY());
            Pea pea = new Pea(position);
            levelController.getLevel().addPea(pea);
            ImageView imageView = levelController.placeInAnchor(pea);

            imageView.setTranslateX(30);
            imageView.setTranslateY(-5);

            PeaController peaController = new PeaController(levelController, pea, imageView);
            peaController.start();
        }

    }
}

