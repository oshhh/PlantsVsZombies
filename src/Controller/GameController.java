package Controller;
import Model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

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

    public void level1(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Level1GUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        controller.setLevel(game.getLevel(0));
        Scene viewScene = new Scene(view,600, 400);
        GridPane grid = (GridPane) (viewScene.lookup("#grid"));
        FileInputStream input = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass.jpg");
        Image image = new Image(input);
        FileInputStream input2 = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Controller/grass2.jpg");
        Image image2 = new Image(input2);

        for(int i = 0; i < NUMBER_OF_ROWS; i ++) {
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
                    }
                });
                GridPane.setRowIndex(imageView, row + 2);
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
