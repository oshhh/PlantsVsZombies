package Controller;

import Model.App;
import Model.LeaderBoard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AppController {

    private App app;

    public void setApp(App app) {
        this.app = app;
    }

    public void enter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LoginGUI.fxml"));
        Parent view = fxmlLoader.load();
        LoginController controller = (LoginController) fxmlLoader.getController();
        controller.setApp(app);
        Scene viewScene = new Scene(view,600, 300);
        controller.setScene(viewScene);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void viewInstructions(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/InstructionsGUI.fxml"));
        Parent view = fxmlLoader.load();
        InstructionsController controller = (InstructionsController) fxmlLoader.getController();
        controller.setApp(app);
        Scene viewScene = new Scene(view,600, 300);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void viewLeaderBoard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LeaderBoardGUI.fxml"));
        Parent view = fxmlLoader.load();
        LeaderBoardController controller = (LeaderBoardController) fxmlLoader.getController();
        controller.setApp(app);
        Scene viewScene = new Scene(view,600, 300);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
