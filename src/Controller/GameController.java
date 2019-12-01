package Controller;
import Database.Main;
import Model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Scene scene;

    public void setUpGame(Game game, Scene scene) {
        this.game = game;
        this.scene = scene;
        setLevelButtonActivate();
        setScore();
    }

    public void setLevelButtonActivate() {
        for(int level = 0; level < Game.NUMBER_OF_LEVELS; level ++) {
            Button button = (Button) scene.lookup("#level" + level) ;
            System.out.println(game);
            System.out.println(game.getScore());
            System.out.println(game.getScore().getCurrentLevel());
            button.setDisable(level > game.getScore().getCurrentLevel());
        }
    }

    public void setScore() {
        ((Label)scene.lookup("#currentLevel")).setText(Integer.toString(game.getScore().getCurrentLevel()));
        ((Label)scene.lookup("#coins")).setText(Integer.toString(game.getScore().getCoins()));
    }

    public void level0(ActionEvent actionEvent) throws IOException {
        int LEVEL = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        controller.setUpLevel(game.getLevel(LEVEL), viewScene);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
    public void level1(ActionEvent actionEvent) throws IOException {
        int LEVEL = 1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        controller.setUpLevel(game.getLevel(LEVEL), viewScene);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();    }
    public void level2(ActionEvent actionEvent) throws IOException {
        int LEVEL = 2;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        controller.setUpLevel(game.getLevel(LEVEL), viewScene);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
    public void level3(ActionEvent actionEvent) throws IOException {
        int LEVEL = 3;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        controller.setUpLevel(game.getLevel(LEVEL), viewScene);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
    public void level4(ActionEvent actionEvent) throws IOException {
        int LEVEL = 4;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        controller.setUpLevel(game.getLevel(LEVEL), viewScene);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.serialize();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AppGUI.fxml"));
        Parent view = fxmlLoader.load();
        AppController controller = (AppController) fxmlLoader.getController();
        controller.setApp(game.getApp());
        Scene viewScene = new Scene(view,1200, 600);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
