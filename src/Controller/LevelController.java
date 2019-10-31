package Controller;

import Model.Level;
import Model.Plant;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;


public class LevelController {

    Level level;
    Scene scene;
//    StickyPlant stickyPlant;
    private boolean isPlantPicked = true;
    private String plant;
    private static final int NUMBER_OF_COLUMNS = 7;

    public void setLevel(Level level) {
        this.level = level;
    }
    public void setScene(Scene scene){
        this.scene = scene;
//        stickyPlant = new StickyPlant();
//        Thread thread = new Thread(stickyPlant);
//        thread.start();
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getPlant() {
        return plant;
    }

    public void createLevel() throws IOException {
        GridPane grid = (GridPane) (scene.lookup("#grid"));
        FileInputStream input = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass.jpg");
        Image image = new Image(input);
        FileInputStream input2 = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass2.jpg");
        Image image2 = new Image(input2);

        for (String plant:  level.getAvailablePlants()) {
            FileInputStream fileInputStream = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/" + plant + ".png");
            Image plantImage = new Image(fileInputStream);
            ImageView plantImageView = new ImageView();
            AnchorPane plantPanel = (AnchorPane) scene.lookup("#plantPanel"+plant);
            plantImageView.setFitWidth(60);
            plantImageView.setFitHeight(60);
            plantImageView.setImage(plantImage);
            plantPanel.getChildren().add(plantImageView);
            plantPanel.onMouseClickedProperty().setValue(e->{
                setPlantPicked(true);
                setPlant(plant);
            });

        }
        for(int i = 0; i < level.NUMBER_OF_ROWS; i ++) {
            for(int j = 0; j < NUMBER_OF_COLUMNS; j ++) {
                final int row = i;
                final int column = j;
                ImageView imageView = new ImageView();
                imageView.setFitWidth(40);
                imageView.setFitHeight(30);
                imageView.onMouseClickedProperty().setValue(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println(row + " " + column);
                        try{
                            placePlant(mouseEvent,row,column);}
                        catch (IOException e){
                            System.out.println("boo");
                        }
                    }
                });
                GridPane.setRowIndex(imageView, row + 3 - level.getLEVEL());
                GridPane.setColumnIndex(imageView, column + 3);
                if (((i+j)%2) == 0) {
                    imageView.setImage(image);
                }
                else{
                    imageView.setImage(image2);
                }
                grid.getChildren().add(imageView);
            }
        }
    }

    public void setPlantPicked(boolean plantPicked) {
        isPlantPicked = plantPicked;
    }


    public void placePlant(javafx.scene.input.MouseEvent mouseEvent, int row, int column) throws IOException{
        if (!isPlantPicked){
            return;
        }
        isPlantPicked=false;
        System.out.println(plant+"level controller");
        GridPane grid = (GridPane) (scene.lookup("#grid"));
        FileInputStream input = null;
        input = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/"+plant+".gif");
        Image image = new Image(input);
        javafx.scene.image.ImageView imageView = new ImageView();
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        GridPane.setRowIndex(imageView, row + 3 - level.getLEVEL());
        GridPane.setColumnIndex(imageView, column + 3);
        imageView.setImage(image);
        grid.getChildren().add(imageView);

    }

//    class StickyPlant implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                if(isPlantPicked) {
//                    try {
//                        GridPane gridPane =  (GridPane) scene.lookup("#grid");
//                        imageView.setX(p.getX());
//                        imageView.setY(p.getY());
//                        Cursor cursor = new Cursor();
//
//                        gridPane.setCursor();
//                    } catch (IOException e) {}
//                }
//            }
//        }
//    }
}