import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    public void loginUser(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent gameView = FXMLLoader.load(getClass().getResource("GameGUI.fxml"));
        Scene gameViewScene = new Scene(gameView,600, 400);
        Stage gameWindow = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        gameWindow.setScene(gameViewScene);
        gameWindow.show();
    }

}
