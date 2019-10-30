package Controller;

import Model.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class LevelController {

    Level level;

    public void setLevel(Level level) {
        this.level = level;
    }

    public void addPlant(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
    }
}