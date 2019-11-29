package Controller;

import Model.Game;
import Model.Level;
import Model.Zombie;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.io.*;

public class ZombieController extends PlaceableController {

    public ZombieController(LevelController levelController, Zombie zombie, ImageView imageView) {
        super(levelController, zombie, imageView);
    }

    @Override
    public void run() {
        boolean prevAttackState = false;
        boolean prevAliveState = true;
        boolean prevMovingState = true;
        while (levelController.getLevel().isRunning()) {
            Zombie zombie = (Zombie) placeable;
            while (levelController.isPause() & levelController.getLevel().isRunning()) {}
            if (!zombie.isAlive()) {
                Platform.runLater(() -> {
                    AnchorPane anchorPane = (AnchorPane) levelController.getScene().lookup("#mainPane");
                    anchorPane.getChildren().remove(imageView);
                });

                levelController.getLevel().removeZombie(zombie);
                zombie.setMoving(false);
                break;
            }
            if (zombie.isMoving()) {
                if (zombie.getPosition().getX()<=levelController.getPosition(0,-1).getX()){
//                        Game Over
                    levelController.getLevel().setRunning(false);
                    Game game = levelController.getLevel().getGame();
                    game.resetLevel(levelController.getLevel().getLEVEL());
                    Platform.runLater(() -> {
                        Node node = levelController.getScene().lookup("#gameOverMenu");
                        node.setDisable(false);
                        node.setVisible(true);
                    });

                }
                zombie.move();
                AnchorPane.setTopAnchor(imageView, (double) zombie.getPosition().getY());
                AnchorPane.setLeftAnchor(imageView, (double) zombie.getPosition().getX());
                try {
                    Thread.sleep(LevelController.ANIMATION_TIMEGAP);
                } catch (InterruptedException e) { }
            }

            if (zombie.isAttacking() ^ prevAttackState) {
                if(zombie.isAttacking()) {
                    imageView.setImage(new Image("Assets/" + zombie.getAttackingImageName()));
                } else {
                    imageView.setImage(new Image("Assets/" + zombie.getImageName()));
                }
            }
            prevAttackState = zombie.isAttacking();
        }
    }
}

