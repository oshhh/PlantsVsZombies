package Controller;

import Model.Plant;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.io.*;

public class PlantController extends PlaceableController{
    public PlantController(LevelController levelController, Plant plant, ImageView imageView){
        super(levelController, plant, imageView);
    }
    @Override
    public void run(){
        while (levelController.getLevel().isRunning()) {
            while (levelController.isPause() & levelController.getLevel().isRunning()) {
            }
            Plant plant = (Plant) placeable;
            if (!plant.isAlive()) {
                Platform.runLater(() -> {
                    GridPane gridPane = (GridPane) levelController.getScene().lookup("#grid");
                    gridPane.getChildren().remove(imageView);
                });
                levelController.getLevel().removePlant(plant);
                break;
            }
        }
    }
}
