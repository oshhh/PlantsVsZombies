package Controller;

import Model.LawnMower;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.io.*;

public class LawnMowerController extends PlaceableController{
    public LawnMowerController(LevelController levelController, LawnMower lawnMower, ImageView imageView){
        super(levelController, lawnMower, imageView);
    }
    @Override
    public void run(){
        while (levelController.getLevel().isRunning()) {
            while (levelController.isPause() & levelController.getLevel().isRunning()) {}
            LawnMower lawnMower = (LawnMower) placeable;
            if(lawnMower.isMowing()){
                lawnMower.move();
                AnchorPane.setTopAnchor(imageView, (double)lawnMower.getPosition().getY());
                AnchorPane.setLeftAnchor(imageView, (double)lawnMower.getPosition().getX());
                try {
                    Thread.sleep(LevelController.ANIMATION_TIMEGAP);
                } catch (InterruptedException e) {}
            }
            if(!lawnMower.isAlive()){
                Platform.runLater(() -> {
                    AnchorPane anchorPane = (AnchorPane) levelController.getScene().lookup("#mainPane");
                    anchorPane.getChildren().remove(imageView);
                });

                levelController.getLevel().removeLawnMower(lawnMower);
                break;
            }
        }

    }
}
