import Controller.*;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static App app;

    public static void main(String[] args) throws IOException {
        app = new App();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/AppGUI.fxml"));
        Parent view = fxmlLoader.load();
        AppController controller = (AppController) fxmlLoader.getController();
        controller.setApp(app);
        Scene viewScene = new Scene(view,600, 300);
        stage.setScene(viewScene);
        stage.show();

    }
}
