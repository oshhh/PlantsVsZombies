import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent view = FXMLLoader.load(getClass().getResource("AppGUI.fxml"));
        Scene viewScene = new Scene(view,600, 400);
        stage.setScene(viewScene);
        stage.show();

    }
}
