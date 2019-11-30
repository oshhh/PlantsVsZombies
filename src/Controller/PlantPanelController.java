package Controller;

import Model.Level;
import Model.PlantPanel;
import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.*;
import java.io.*;

public class PlantPanelController extends Thread {
    public static final int NEXT_PURCHASE_TIME = 5000;
    public static final int DROP_SHADOW_WIDTH = 50;

    public static final double DISABLED_OPACITY = 0.5;
    public static final double NORMAL_OPACITY = 0.8;
    public static final double SELECTED_OPACITY = 1;


    private PlantPanel plantPanel;
    private LevelController levelController;

    public PlantPanelController(PlantPanel plantPanel, LevelController levelController) {
        this.plantPanel = plantPanel;
        this.levelController = levelController;
    }

    @Override
    public void run() {
        for(Class plant : plantPanel.getAvailablePlants()) {
            AnchorPane anchorPane = plantPanel.getAnchorPaneHashMap().get(plant);
            ImageView imageView = (ImageView) anchorPane.getChildren().get(0);
            imageView.setOpacity(NORMAL_OPACITY);
        }

        while (levelController.getLevel().isRunning()) {
            while (levelController.isPause() & levelController.getLevel().isRunning()) {}

            for(Class plant : plantPanel.getAvailablePlants()) {
                AnchorPane anchorPane = plantPanel.getAnchorPaneHashMap().get(plant);
                plantPanel.getPlantDisabled().put(plant, (System.currentTimeMillis() - plantPanel.plantLastPlaced(plant) < NEXT_PURCHASE_TIME) | (levelController.getLevel().getGame().getScore().getSunPower() < PlantPanel.getPrice(plant)));
                ImageView imageView = (ImageView) anchorPane.getChildren().get(0);

                if(plantPanel.isPlantDisabled(plant) & imageView.getOpacity() == NORMAL_OPACITY) {
                    imageView.setOpacity(DISABLED_OPACITY);
                }

                if(!plantPanel.isPlantDisabled(plant) & imageView.getOpacity() == DISABLED_OPACITY) {
                    imageView.setOpacity(NORMAL_OPACITY);
                }

                if(!plantPanel.isPlantSelected(plant) & imageView.getOpacity() == SELECTED_OPACITY) {
                    imageView.setOpacity(NORMAL_OPACITY);

                }
                if(plantPanel.isPlantSelected(plant) & imageView.getOpacity() == NORMAL_OPACITY){
                    imageView.setOpacity(SELECTED_OPACITY);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }
}
