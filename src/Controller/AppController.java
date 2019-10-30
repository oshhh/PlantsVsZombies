package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AppController {

    public void enter(ActionEvent actionEvent) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("../View/LoginGUI.fxml"));
        Scene viewScene = new Scene(view,600, 400);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void viewInstructions(ActionEvent actionEvent) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("../View/InstructionsGUI.fxml"));
        Scene viewScene = new Scene(view,600, 400);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void viewLeaderBoard(ActionEvent actionEvent) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("../View/LeaderBoardGUI.fxml"));
        Scene viewScene = new Scene(view,600, 400);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
