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
        Scene viewScene = new Scene(view,600, 300);
        controller.setScene(viewScene);
        controller.setUpLevel();
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
        Scene viewScene = new Scene(view,600, 300);
        controller.setScene(viewScene);
        controller.setUpLevel();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();    }
    public void level2(ActionEvent actionEvent) throws IOException {
        int LEVEL = 2;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        controller.setLevel(game.getLevel(LEVEL));
        Scene viewScene = new Scene(view,600, 300);
        controller.setScene(viewScene);
        controller.setUpLevel();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AppGUI.fxml"));
        Parent view = fxmlLoader.load();
        AppController controller = (AppController) fxmlLoader.getController();
        controller.setApp(game.getApp());
        Scene viewScene = new Scene(view,600, 300);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
