package Controller;

import Model.Level;
import Model.Plant;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class LevelController {

    Level level;
    Scene scene;
    private boolean isPlantPicked = true;
    private String plant;

    public void setLevel(Level level) {
        this.level = level;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getPlant() {
        return plant;
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
        GridPane.setRowIndex(imageView, row + 2);
        GridPane.setColumnIndex(imageView, column + 3);
        imageView.setImage(image);
        grid.getChildren().add(imageView);

    }
}