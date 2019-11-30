package Controller;

import Model.CherryBomb;
import Model.Plant;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.io.*;

public class PlantController extends PlaceableController{

    private long timeCreated;
    private static final int CHERRY_BOMB_TIME = 2000;
    public PlantController(LevelController levelController, Plant plant, ImageView imageView){
        super(levelController, plant, imageView);
        timeCreated = System.currentTimeMillis();

    }
    @Override
    public void run(){
        while (levelController.getLevel().isRunning()) {
            while (levelController.isPause() & levelController.getLevel().isRunning()) {
            }
            Plant plant = (Plant) placeable;
            if (plant.getClass().equals(CherryBomb.class) & System.currentTimeMillis() - timeCreated >= CHERRY_BOMB_TIME){
                plant.setAlive(false);
            }

            if (!plant.isAlive()) {
                levelController.getLevel().removePlant(plant);
                if (!plant.getClass().equals(CherryBomb.class)) {
                    imageView.setImage(new Image("Assets/Dead" + plant.getImageName()));
                }
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){}
                Platform.runLater(() -> {
                    GridPane gridPane = (GridPane) levelController.getScene().lookup("#grid");
                    gridPane.getChildren().remove(imageView);
                });
                break;
            }
        }
    }
}
