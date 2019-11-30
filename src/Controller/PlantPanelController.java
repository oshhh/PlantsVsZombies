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
    public static final int DROP_SHADOW_WIDTH = 100;
    private static DropShadow whiteBorderGlow;
    {
        whiteBorderGlow= new DropShadow();
        whiteBorderGlow.setOffsetY(0f);
        whiteBorderGlow.setOffsetX(0f);
        whiteBorderGlow.setColor(Color.WHITE);
        whiteBorderGlow.setWidth(DROP_SHADOW_WIDTH);
        whiteBorderGlow.setHeight(DROP_SHADOW_WIDTH);
    }
    private static DropShadow yellowBorderGlow;
    {
        yellowBorderGlow= new DropShadow();
        yellowBorderGlow.setOffsetY(0f);
        yellowBorderGlow.setOffsetX(0f);
        yellowBorderGlow.setColor(Color.YELLOW);
        yellowBorderGlow.setWidth(DROP_SHADOW_WIDTH);
        yellowBorderGlow.setHeight(DROP_SHADOW_WIDTH);
    }
    private static DropShadow greyBorderGlow;
    {
        greyBorderGlow= new DropShadow();
        greyBorderGlow.setOffsetY(0f);
        greyBorderGlow.setOffsetX(0f);
        greyBorderGlow.setColor(Color.BLUE);
        greyBorderGlow.setWidth(DROP_SHADOW_WIDTH);
        greyBorderGlow.setHeight(DROP_SHADOW_WIDTH);
    }

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
            anchorPane.setEffect(whiteBorderGlow);
        }

        while (levelController.getLevel().isRunning()) {
            while (levelController.isPause() & levelController.getLevel().isRunning()) {}

            for(Class plant : plantPanel.getAvailablePlants()) {
                AnchorPane anchorPane = plantPanel.getAnchorPaneHashMap().get(plant);
                plantPanel.getPlantDisabled().put(plant, System.currentTimeMillis() - plantPanel.plantLastPlaced(plant) < NEXT_PURCHASE_TIME);

                if(plantPanel.isPlantDisabled(plant) ^ !anchorPane.isDisable()) {
                    if(plantPanel.isPlantDisabled(plant)) {
                        anchorPane.setEffect(greyBorderGlow);
                    } else {
                        anchorPane.setEffect(whiteBorderGlow);
                    }
                }

                if(!plantPanel.isPlantSelected(plant) & anchorPane.getEffect().equals(yellowBorderGlow)) {
                    anchorPane.setEffect(whiteBorderGlow);
                }
                if(plantPanel.isPlantSelected(plant) & anchorPane.getEffect().equals(whiteBorderGlow)){
                    anchorPane.setEffect(yellowBorderGlow);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
