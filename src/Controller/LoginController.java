package Controller;

import Model.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private App app;
    private Scene scene;

    public void setApp(App app) {
        this.app = app;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void loginUser(javafx.event.ActionEvent actionEvent) throws IOException {
        String name = ((TextField) scene.lookup("#loginName")).getCharacters().toString();
        System.out.println(name);

        Player player = app.findPlayer(name);
        Game game = player.getGame();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/GameGUI.fxml"));
        Parent view = fxmlLoader.load();
        GameController controller = (GameController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        controller.setUpGame(game, viewScene);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AppGUI.fxml"));
        Parent view = fxmlLoader.load();
        AppController controller = (AppController) fxmlLoader.getController();
        controller.setApp(app);
        Scene viewScene = new Scene(view,1200, 600);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }}
