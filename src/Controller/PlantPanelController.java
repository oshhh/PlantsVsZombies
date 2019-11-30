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
    private static DropShadow whiteBorderGlow;
    {
        whiteBorderGlow= new DropShadow();
        whiteBorderGlow.setOffsetY(0f);
        whiteBorderGlow.setOffsetX(0f);
        whiteBorderGlow.setColor(Color.WHITE);
        whiteBorderGlow.setWidth(50);
        whiteBorderGlow.setHeight(50);
    }
    private static DropShadow yellowBorderGlow;
    {
        yellowBorderGlow= new DropShadow();
        yellowBorderGlow.setOffsetY(0f);
        yellowBorderGlow.setOffsetX(0f);
        yellowBorderGlow.setColor(Color.WHITE);
        yellowBorderGlow.setWidth(50);
        yellowBorderGlow.setHeight(50);
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
            ImageView imageView = plantPanel.getImageView().get(plant);
            imageView.setEffect(whiteBorderGlow);
        }

        while (levelController.getLevel().isRunning()) {
            while (levelController.isPause() & levelController.getLevel().isRunning()) {}

            for(Class plant : plantPanel.getAvailablePlants()) {
                ImageView imageView = plantPanel.getImageView().get(plant);
                plantPanel.getPlantDisabled().put(plant, System.currentTimeMillis() - plantPanel.plantLastPlaced(plant) < NEXT_PURCHASE_TIME);

                if(plantPanel.isPlantDisabled(plant) ^ !imageView.isDisable()) {
                    imageView.setDisable(!imageView.isDisable());
                }

                if(!plantPanel.isPlantSelected(plant) & imageView.getEffect().equals(yellowBorderGlow)) {
                    imageView.setEffect(whiteBorderGlow);
                }
                if(plantPanel.isPlantSelected(plant) & imageView.getEffect().equals(whiteBorderGlow)){
                    imageView.setEffect(yellowBorderGlow);
                }
                System.out.println(plant + " " + plantPanel.getPlantDisabled().get(plant) + " " + plantPanel.isPlantSelected(plant));
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
