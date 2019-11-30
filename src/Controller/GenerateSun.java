package Controller;

import Model.Plant;
import Model.Position;
import Model.SunFlower;
import Model.SunToken;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.*;
import java.io.*;

public class GenerateSun implements Runnable {
    LevelController levelController;

    public GenerateSun(LevelController levelController) {
        this.levelController = levelController;
    }

    public void addSunToken(Position position, boolean moving) {
        SunToken sunToken = new SunToken(position, moving);
        ImageView imageView = levelController.placeInAnchor(sunToken);
        levelController.getLevel().addSunToken(sunToken);
        SunTokenController sunTokenController = new SunTokenController(levelController, sunToken, imageView);
        sunTokenController.start();

        // Add listener
        imageView.setOnMouseClicked(mouseEvent -> {
            if(levelController.isPause())   return;
            levelController.getLevel().collectSun();
            System.out.println("here");
            levelController.setSunScore();
            sunToken.setAlive(false);
            AnchorPane anchorPane = (AnchorPane) (levelController.getScene().lookup("#mainPane"));
            anchorPane.getChildren().remove(imageView);
        });

    }

    @Override
    public void run() {
        Random random = new Random();
        int col=random.nextInt(7) + LevelController.COLUMN_OFFSET;
        addSunToken(levelController.getPosition(LevelController.SKY_ROW, col), true);

        for (Plant plant : levelController.getLevel().getPlants()) {
            if (!plant.getClass().equals(SunFlower.class)) continue;
            SunFlower sunFlower = (SunFlower) plant;
            if (sunFlower.getSunFlowerFlag()) continue;
            Position position = plant.getPosition();
            addSunToken(position, false);
        }
    }

}

