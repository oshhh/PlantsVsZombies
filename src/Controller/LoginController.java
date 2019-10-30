package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {

    public void loginUser(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("../View/GameGUI.fxml"));
        Scene viewScene = new Scene(view,600, 400);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
