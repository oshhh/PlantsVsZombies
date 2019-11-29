package Controller;

import Model.Level;
import Model.Pea;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.io.*;

public class PeaController extends PlaceableController{
    public PeaController(LevelController levelController, Pea pea, ImageView imageView){
        super(levelController, pea, imageView);
    }
    @Override
    public void run(){
        while (levelController.getLevel().isRunning()) {
            while (levelController.isPause() & levelController.getLevel().isRunning()) {}

            Pea pea = (Pea) placeable;
            if (pea.isAlive()){
                pea.move();
                AnchorPane.setTopAnchor(imageView, (double)pea.getPosition().getY());
                AnchorPane.setLeftAnchor(imageView, (double)pea.getPosition().getX());
                try {
                    Thread.sleep(LevelController.ANIMATION_TIMEGAP);
                } catch (InterruptedException e) {}
            }
            else{
                Platform.runLater(() -> {
                    AnchorPane anchorPane = (AnchorPane) levelController.getScene().lookup("#mainPane");
                    anchorPane.getChildren().remove(imageView);
                });

                levelController.getLevel().removePea(pea);
                break;
            }
        }


    }
}
