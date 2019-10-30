package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GameController {

    public void level1(ActionEvent actionEvent) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("../View/Level1GUI.fxml"));
        Scene viewScene = new Scene(view,600, 400);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }



}
