package Controller;
import Model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;


public class GameController {
    public static final int NUMBER_OF_ROWS = 3;
    public static final int NUMBER_OF_COLUMNS = 7;
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public void level0(ActionEvent actionEvent) throws IOException {
        int LEVEL = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        controller.setLevel(game.getLevel(LEVEL));
        Scene viewScene = new Scene(view,600, 400);
        controller.setScene(viewScene);
        controller.createLevel();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
    public void level1(ActionEvent actionEvent) throws IOException {
        int LEVEL = 1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        controller.setLevel(game.getLevel(LEVEL));
        Scene viewScene = new Scene(view,600, 400);
        controller.setScene(viewScene);
        GridPane grid = (GridPane) (viewScene.lookup("#grid"));
        FileInputStream input = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass.jpg");
        Image image = new Image(input);
        FileInputStream input2 = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass2.jpg");
        Image image2 = new Image(input2);

        for (String plant:  game.getLevel(LEVEL).getAvailablePlants()) {
            FileInputStream fileInputStream = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/" + plant + ".png");
            Image plantImage = new Image(fileInputStream);
            ImageView plantImageView = new ImageView();
            AnchorPane plantPanel = (AnchorPane) viewScene.lookup("#plantPanel"+plant);
            plantImageView.setFitWidth(60);
            plantImageView.setFitHeight(60);
            plantImageView.setImage(plantImage);
            plantPanel.getChildren().add(plantImageView);
            plantPanel.onMouseClickedProperty().setValue(e->{
                controller.setPlantPicked(true);
                controller.setPlant(plant);
            });

        }
        for(int i = 0; i < game.getLevel(LEVEL).NUMBER_OF_ROWS; i ++) {
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
                            controller.placePlant(mouseEvent,row,column);}
                        catch (IOException e){
                            System.out.println("boo");
                        }
                    }
                });
                GridPane.setRowIndex(imageView, row + 3 - LEVEL);
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

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
    public void level2(ActionEvent actionEvent) throws IOException {
        int LEVEL = 2;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        controller.setLevel(game.getLevel(LEVEL));
        Scene viewScene = new Scene(view,600, 400);
        controller.setScene(viewScene);
        GridPane grid = (GridPane) (viewScene.lookup("#grid"));
        FileInputStream input = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass.jpg");
        Image image = new Image(input);
        FileInputStream input2 = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass2.jpg");
        Image image2 = new Image(input2);

        for (String plant:  game.getLevel(LEVEL).getAvailablePlants()) {
            FileInputStream fileInputStream = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/" + plant + ".png");
            Image plantImage = new Image(fileInputStream);
            ImageView plantImageView = new ImageView();
            AnchorPane plantPanel = (AnchorPane) viewScene.lookup("#plantPanel"+plant);
            plantImageView.setFitWidth(60);
            plantImageView.setFitHeight(60);
            plantImageView.setImage(plantImage);
            plantPanel.getChildren().add(plantImageView);
            plantPanel.onMouseClickedProperty().setValue(e->{
                controller.setPlantPicked(true);
                controller.setPlant(plant);
            });

        }
        for(int i = 0; i < game.getLevel(LEVEL).NUMBER_OF_ROWS; i ++) {
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
                            controller.placePlant(mouseEvent,row,column);}
                        catch (IOException e){
                            System.out.println("boo");
                        }
                    }
                });
                GridPane.setRowIndex(imageView, row + 3 - LEVEL);
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

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
