package Controller;

import Model.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/GameGUI.fxml"));
        Parent view = fxmlLoader.load();
        GameController controller = (GameController) fxmlLoader.getController();
        controller.setGame(app.findPlayer(name).getGame());
        Scene viewScene = new Scene(view,600, 400);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
